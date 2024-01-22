package net.youssfi.customerservice.sec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities,jwt.getClaim("preferred_username"));
    }
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String , Object> realmAccess;
        Collection<String> roles;
        if(jwt.getClaim("realm_access")==null){
            return Set.of();
        }
        realmAccess = jwt.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
    }

}

/**
 * {
 *   "exp": 1704728741,
 *   "iat": 1704728441,
 *   "jti": "a43ac58e-f089-4be1-aa01-524849ee53c3",
 *   "iss": "http://localhost:8080/realms/ebank-realm",
 *   "aud": "account",
 *   "sub": "3098bfae-9345-476f-94dc-cd0966ccf062",
 *   "typ": "Bearer",
 *   "azp": "ebank-client",
 *   "session_state": "8accf00d-d470-468d-bdef-bceed38a4a50",
 *   "acr": "1",
 *   "allowed-origins": [
 *     "/*"
 *   ],
 *   "realm_access": {
 *     "roles": [
 *       "offline_access",
 *       "default-roles-ebank-realm",
 *       "uma_authorization",
 *       "USER"
 *     ]
 *   },
 *   "resource_access": {
 *     "account": {
 *       "roles": [
 *         "manage-account",
 *         "manage-account-links",
 *         "view-profile"
 *       ]
 *     }
 *   },
 *   "scope": "email profile",
 *   "sid": "8accf00d-d470-468d-bdef-bceed38a4a50",
 *   "email_verified": false,
 *   "name": "YOUSSFI Mohamed",
 *   "preferred_username": "user1",
 *   "given_name": "YOUSSFI",
 *   "family_name": "Mohamed",
 *   "email": "user1@gmail.com"
 * }
 */