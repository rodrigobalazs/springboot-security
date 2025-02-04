package com.rbalazs.securityapi.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Represents the Authentication Token associated to a given User once it´s sucessfully logged-in into the Application.
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