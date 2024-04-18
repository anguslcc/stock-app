package org.finance.stockapp.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

  private final Converter<Jwt, Collection<GrantedAuthority>> roleConverter;

  private static final String ROLE_STOCK_READ = "read-role";
  private static final String ROLE_STOCK_FULL_CONTROL = "full-control-role";

  public GatewaySecurityConfig(Converter<Jwt, Collection<GrantedAuthority>> roleConverter) {
    this.roleConverter = roleConverter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(roleConverter);

    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorization -> authorization
            .requestMatchers("/stocks").hasAuthority(ROLE_STOCK_FULL_CONTROL)
            .requestMatchers("/stocks/**").hasAuthority(ROLE_STOCK_READ)
            .anyRequest().permitAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
        );
    return http.build();
  }

}
