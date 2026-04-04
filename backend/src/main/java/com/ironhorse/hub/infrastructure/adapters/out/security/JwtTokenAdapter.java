package com.ironhorse.hub.infrastructure.adapters.out.security;

import com.ironhorse.hub.domain.AuthToken;
import com.ironhorse.hub.domain.TokenProvider;
import com.ironhorse.hub.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Adaptador para provedor de tokens JWT utilizando JJWT.
 */
@Component
public class JwtTokenAdapter implements TokenProvider {

    private final SecretKey key;
    private final long expiration;

    public JwtTokenAdapter(@Value("${ironhorse.jwt.secret}") String secret,
                         @Value("${ironhorse.jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    @Override
    public AuthToken generateToken(User user) {
        String token = Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
        
        return new AuthToken(token);
    }

    @Override
    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
