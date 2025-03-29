package com.rbalazs.securityapi.security;

import com.rbalazs.securityapi.enums.AppValidations;
import com.rbalazs.securityapi.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

    @Value("${jwt.auth.token.secret.key}")
    private String jwtAuthTokenSecretKey;

    @Value("${jwt.auth.token.expiration.time}")
    private long jwtAuthTokenExpirationTime;

    /**
     * Generates a JWT Authentication Token based on the user´s email address and user´s role given as parameters.
     */
    public String generateToken(final String userEmail, final String userRole) {

        if(StringUtils.isEmpty(jwtAuthTokenSecretKey)){
            throw new CustomException(AppValidations.EMPTY_SECRET_KEY);
        }

        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + jwtAuthTokenExpirationTime);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("role", userRole)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validates whether the JWT Authentication Token given as parameter is valid or not for the user
     * given as parameter.
     */
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
        return Keys.hmacShaKeyFor(jwtAuthTokenSecretKey.getBytes());
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
