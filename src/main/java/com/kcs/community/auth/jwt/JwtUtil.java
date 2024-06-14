package com.kcs.community.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
    private SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}")String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.get("username", String.class);
    }

    public String getRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    public String getCategory(String token) {
        Claims claims = getClaims(token);
        return claims.get("category", String.class);
    }

    public Boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public String generateToken(String category, String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey, SIG.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
