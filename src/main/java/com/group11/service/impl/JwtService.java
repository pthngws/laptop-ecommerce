package com.group11.service.impl;

import com.group11.entity.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    // Extract username (email) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generate token with custom claims
    public String generateToken(Map<String, Object> extraClaims, UserEntity userDetails) {
        extraClaims.put("role", userDetails.getRoleName());
        extraClaims.put("name", userDetails.getName());
        extraClaims.put("address", userDetails.getAddress());
        extraClaims.put("phone", userDetails.getPhone());
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Generate token from UserEntity
    public String generateToken(UserEntity userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Get the expiration time (in minutes) of the JWT
    public long getExpirationTime() {
        return jwtExpiration;
    }

    // Build the JWT with claims and expiration
    private String buildToken(Map<String, Object> extraClaims, UserEntity userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  // Use HS256 for signing
                .compact();
    }

    // Validate token against UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract all claims from the JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())  // Set the signing key for verification
                .build()
                .parseClaimsJws(token)
                .getBody();  // Extract claims
    }

    // Get the signing key from the secret key (Base64 decoded)
    private SecretKey getSignInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);  // Return the signing key for JWT
    }
}
