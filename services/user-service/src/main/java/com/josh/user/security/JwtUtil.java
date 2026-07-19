package com.josh.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtil handles generating, parsing, and validating JSON Web Tokens (JWT).
 * 
 * --- JWT ANATOMY (Base64URL encoded parts separated by dots): ---
 * 1. HEADER: Contains algorithm (e.g. HS256) and token type (JWT).
 * 2. PAYLOAD: Contains claims (e.g. email, role, expiration).
 * 3. SIGNATURE: Verified by signing Header + Payload using the secret key, ensuring the token wasn't tampered with.
 */
@Slf4j
@Component
public class JwtUtil {

    // Development Secret Key. In production, this MUST be injected from environment variables or a Secret Vault.
    private static final String SECRET_STRING = "JoshAirlineSecretSuperSecureKeyForJwtTokenGenerationMustBe256BitsLongMinimum!";
    
    // Convert secret string to cryptographic Key object using HMAC-SHA key generator
    private final Key key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // Expiration duration (e.g. 24 hours in milliseconds)
    private static final long EXPIRATION_TIME = 86400000; 

    /**
     * Generate token containing user's email and role claims.
     */
    public String generateToken(String email, String role) {
        log.info("Generating JWT token for user: {}", email);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        // Put custom claims in the token payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // subject represents the identity (email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256) // Sign using HMAC SHA-256
                .compact();
    }

    /**
     * Extract the subject (email) from the token.
     */
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extract the role claim from the token.
     */
    public String getRoleFromToken(String token) {
        return (String) getClaims(token).get("role");
    }

    /**
     * Check if token is expired.
     */
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    /**
     * Validate token integrity against cryptographic signature and expiration.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid or expired JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Parse and extract all claims from token.
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
