package com.rbalazs.securityapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Represents the JWT Token Filter which is executed once an HTTP REQUEST is made.
 *
 * @author Rodrigo Balazs
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenUtils jwtTokenUtils;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils, UserDetailsService userDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
    }

    // whenever a CURL executes this is the first method executes, check whether the Token is present or not in the request Header
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtTokenUtils.resolveToken(request);
        if (token != null && jwtTokenUtils.validateToken(token, userDetailsService.loadUserByUsername(jwtTokenUtils.extractUsername(token)))) {
            String username = jwtTokenUtils.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}