package org.finance.stockapp.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final Logger LOG = LoggerFactory.getLogger(KeycloakRoleConverter.class);
  private static final String KEYCLOAK_REALM_ACCESS_KEY = "realm_access";
  private static final String KEYCLOAK_ROLE_KEY = "roles";

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {

    Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get(KEYCLOAK_REALM_ACCESS_KEY);

    if (realmAccess == null || realmAccess.isEmpty()) {
      return new ArrayList<>();
    }

    List<String> roleList = (List<String>) realmAccess.get(KEYCLOAK_ROLE_KEY);

    LOG.info("Roles in JWT: {} ", roleList);

    return new ArrayList<>(roleList.stream().map(roleName -> "ROLE_" + roleName)
        .map(SimpleGrantedAuthority::new).toList());
  }


}
