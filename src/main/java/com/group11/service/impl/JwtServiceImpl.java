package com.group11.service.impl;

import com.group11.entity.UserEntity;
import com.group11.service.IJwtService;
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
public class JwtServiceImpl implements IJwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserEntity userDetails) {
        extraClaims.put("role", userDetails.getRoleName());
        extraClaims.put("name", userDetails.getName());
        extraClaims.put("address", userDetails.getAddress());
        extraClaims.put("phone", userDetails.getPhone());
        extraClaims.put("userid", userDetails.getUserID());
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    @Override
    public String generateToken(UserEntity userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public long getExpirationTime() {
        return jwtExpiration;
    }

    @Override
    public String buildToken(Map<String, Object> extraClaims, UserEntity userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  // Use HS256 for signing
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())  // Set the signing key for verification
                .build()
                .parseClaimsJws(token)
                .getBody();  // Extract claims
    }

    @Override
    public SecretKey getSignInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);  // Return the signing key for JWT
    }

    @Override
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userid", Long.class));
    }
}
