package com.rbalazs.securityapi.security;

import com.rbalazs.securityapi.enums.AppValidations;
import com.rbalazs.securityapi.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Provides some utilities to generate/validate JWT Authentication Tokens.
 *
 * @author Rodrigo Balazs
 */
@Component
public class JwtTokenUtils {

    @Value("${jwt.token.secret.key}")
    private String jwtTokenSecretKey;

    @Value("${jwt.token.expiration}")
    private long jwtTokenExpirationTime;

    /**
     * Generates a JWT Authentication Token based on the user email address and role given as parameters.
     *
     * @param userEmail the user email address
     * @param userRole the user role
     * @return the generated JWT Authentication Token associated to the user email address/role.
     */
    public String generateToken(final String userEmail, final String userRole) {

        if(StringUtils.isEmpty(jwtTokenSecretKey)){
            throw new CustomException(AppValidations.EMPTY_SECRET_KEY);
        }

        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + jwtTokenExpirationTime);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("role", userRole)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(final String token) {
        return extractAllClaims(token).getSubject();
    }

    public String resolveToken(final HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Boolean isTokenExpired(final String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtTokenSecretKey.getBytes());
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
