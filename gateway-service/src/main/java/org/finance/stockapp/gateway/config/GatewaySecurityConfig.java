package org.finance.stockapp.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

    ReactiveJwtAuthenticationConverterAdapter jwtAdapter = new ReactiveJwtAuthenticationConverterAdapter(
        jwtAuthenticationConverter);

    http
        .csrf(CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/stock-data/actuator/**").permitAll()  // Public endpoints
            //.pathMatchers("/private/**").authenticated()  // Private endpoints
            //.pathMatchers("/admin/**").hasAuthority("ROLE_ADMIN")  // Admin-only endpoints
            .anyExchange().authenticated()  // Default policy
        ).oauth2ResourceServer(oauth2ResourceServerCustomizer ->
            oauth2ResourceServerCustomizer.jwt(
                jwtCustomizer -> jwtCustomizer.jwtAuthenticationConverter(jwtAdapter)));

    return http.build();
  }
}
