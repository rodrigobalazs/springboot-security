package com.rbalazs.securityapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Represents the JWT Token Filter which is executed as part of the Spring Security Filters Chain once an
 * HTTP REQUEST is made.
 *
 * @author Rodrigo Balazs
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
    private JwtTokenUtils jwtTokenUtils;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenFilter(final JwtTokenUtils jwtTokenUtils, final UserDetailsService userDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is executed whenever an HTTP REQUEST is made, first checks whether the Authentication Token is present in
     * the HTTP Request Header. If the Token is present, validates the Token and sets the {@link JwtAuthenticationToken}
     * (which includes user´s details) into the SecurityContextHolder.
     * If the Authentication Token is not present, it continues the Filter Chain.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.info("starts to execute jwtTokenFilter.doFilterInternal()");
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