package com.msidentity.application.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    private final JwtProperties jwtProperties;
    private Key signKey;

    @Autowired
    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        this.signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token);
        } catch (Exception e) {
            throw new AuthenticationServiceException("invalid access");
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userName);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userName)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
                    .signWith(signKey, SignatureAlgorithm.HS256).compact();
        } catch (Exception e) {
            throw new RuntimeException("invalid access");
        }
    }
}