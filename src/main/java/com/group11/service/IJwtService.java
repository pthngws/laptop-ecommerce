package com.group11.service;

import com.group11.entity.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(Map<String, Object> extraClaims, UserEntity userDetails);

    String generateToken(UserEntity userDetails);

    long getExpirationTime();

    String buildToken(Map<String, Object> extraClaims, UserEntity userDetails, long expiration);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);

    SecretKey getSignInKey();
    Long extractUserId(String token);
}
