package com.rbalazs.securityapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
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

    public String generateToken(String userEmail, String userRole) {

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

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtTokenSecretKey.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
