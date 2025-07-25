package colepp.app.wealthwisebackend.auth.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {
    final long tokenExpiration = 86400;

    @Value("${spring.jwt}")
    private String secret;

    public String generateToken(String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .issuedAt(new Date())
                .expiration(new  Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}

