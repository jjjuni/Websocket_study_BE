package com.websocket.websocketStudy.domain.token;


import com.websocket.websocketStudy.domain.user.data.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    @Value("${spring.jwt.access-expiration}")
    private long ACCESS_EXPIRATION;

    @Value("${spring.jwt.refresh-expiration}")
    private long REFRESH_EXPIRATION;

    private final JwtUtil jwtUtil;

    public String generateAccessToken(User user){
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject(user.getEmail())
                .setExpiration(new Date(now + ACCESS_EXPIRATION))
                .signWith(jwtUtil.getSigningKey(JwtUtil.tokenType.ACCESS), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user){
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(user.getEmail())
                .setExpiration(new Date(now + REFRESH_EXPIRATION))
                .signWith(jwtUtil.getSigningKey(JwtUtil.tokenType.REFRESH), SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("Role", user.getRole());
        return claims;
    }
}
