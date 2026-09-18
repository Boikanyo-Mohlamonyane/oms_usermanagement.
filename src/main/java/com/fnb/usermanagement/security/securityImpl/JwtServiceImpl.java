package com.fnb.usermanagement.security.securityImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.fnb.usermanagement.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fnb.usermanagement.security.JwtService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;


    private SecretKey signingkey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() +  expirationMs);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("customerId", user.getCustomer_id())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingkey())
                .compact();
    }

    @Override
    public boolean validateToken(String token, String email) {
        try {
            Claims claims = parseClaims(token);
            boolean usernameMatched = claims.getSubject().equals(email);
            boolean notExpired = claims.getExpiration().after(new Date());
            return usernameMatched && notExpired;
        } catch (ExpiredJwtException e){
            return false;
        }
    }

    @Override
    public String extractEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }


    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}