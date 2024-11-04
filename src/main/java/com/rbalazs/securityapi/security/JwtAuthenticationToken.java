package com.rbalazs.securityapi.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Represents the Authentication Token once a given User logged-in into the App.
 *
 * @author Rodrigo Balazs
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;
    private String token;

    public JwtAuthenticationToken(final UserDetails principal, final String token,
                                  final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}