package colepp.app.wealthwisebackend.common.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;



@RequiredArgsConstructor
@Service
public class JwtService {

    final long refreshTokenExpiration = 604800;
    final long accessTokenExpiration = 1200;


    @Value("${spring.jwt}")
    private String secret;

    public String generateAccessToken(String email) {
        return Jwts
                .builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new  Date(System.currentTimeMillis() + 1000 * accessTokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts
            .builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 *refreshTokenExpiration))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
    }

    public boolean isValidToken(String token) {
        try{
            var claims = getClaimsFromToken(token);
            return claims.getExpiration().after(new Date());
        }catch (JwtException ex){
            return false;
        }
    }

    public String formatToken(String token) {
        if(token == null || !token.startsWith("Bearer ")){
            return null;
        }
        return token.replace("Bearer ", "");
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Claims getClaimsFromToken(String token) {
//        System.out.println(token);
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

