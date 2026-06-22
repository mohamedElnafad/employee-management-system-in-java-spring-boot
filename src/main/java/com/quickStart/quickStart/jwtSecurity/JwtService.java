package com.quickStart.quickStart.jwtSecurity;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    // 1. Generate a secure cryptographic signing key from the secret
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return this.generateToken(new HashMap<>(), username);
    }

    // 2. Generate the token using the username/subject
    public String generateToken(Map<String, Object> extraClaims, String username) {

        return Jwts.builder()
                .claims(extraClaims) // Set custom data
                .subject(username) // Set the unique username identifier
                .issuedAt(new Date(System.currentTimeMillis())) // Creation time
                .expiration(new Date(System.currentTimeMillis() + 2000 * 60 * 60)) // Expiry: 2 hour
                .signWith(getSigningKey()) // Crypto signature
                .compact(); // Build into a string
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Helper method to parse the token and verify its signature
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Use the same signing key method from earlier
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

}
