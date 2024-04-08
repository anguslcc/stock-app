package org.finance.stockapp.gateway.config;

import java.util.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

  private final Converter<Jwt, Collection<GrantedAuthority>> roleConverter;

  private static final String ROLE_STOCK_READ = "read-role";
  private static final String ROLE_STOCK_FULL_CONTROL = "full-control-role";

  public GatewaySecurityConfig(Converter<Jwt, Collection<GrantedAuthority>> roleConverter) {
    this.roleConverter = roleConverter;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(roleConverter);

    ReactiveJwtAuthenticationConverterAdapter jwtAdapter = new ReactiveJwtAuthenticationConverterAdapter(
        jwtAuthenticationConverter);

    http
        .csrf(CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/stock-data/stocks").hasAuthority(ROLE_STOCK_FULL_CONTROL)
            .pathMatchers("/stock-data/stocks/**").hasAuthority(ROLE_STOCK_READ)
            .anyExchange().permitAll()
        ).oauth2ResourceServer(oauth2ResourceServerCustomizer ->
            oauth2ResourceServerCustomizer.jwt(
                jwtCustomizer -> jwtCustomizer.jwtAuthenticationConverter(jwtAdapter)));

    return http.build();
  }
}
