package com.websocket.websocketStudy.domain.token.service;


import com.websocket.websocketStudy.apiPayload.code.status.ErrorStatus;
import com.websocket.websocketStudy.apiPayload.exception.ErrorException;
import com.websocket.websocketStudy.config.security.CustomUserDetailsService;
import com.websocket.websocketStudy.domain.token.JwtGenerator;
import com.websocket.websocketStudy.domain.token.JwtUtil;
import com.websocket.websocketStudy.domain.token.data.RefreshToken;
import com.websocket.websocketStudy.domain.token.data.enums.JwtRule;
import com.websocket.websocketStudy.domain.token.repository.RefreshTokenRepository;
import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtGenerator jwtGenerator;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${spring.jwt.access-expiration}")
    private long ACCESS_EXPIRATION;

    @Value("${spring.jwt.refresh-expiration}")
    private long REFRESH_EXPIRATION;

    public String generateAccessToken(HttpServletResponse response, User request){
        String accessToken = jwtGenerator.generateAccessToken(request);
        ResponseCookie cookie = setTokenToCookie(JwtRule.ACCESS_PREFIX.getValue(), accessToken, ACCESS_EXPIRATION / 1000);
        response.addHeader(JwtRule.JWT_ISSUE_HEADER.getValue(), cookie.toString());

        return accessToken;
    }

    @Transactional
    public String generateRefreshToken(HttpServletResponse response, User request){
        String refreshToken = jwtGenerator.generateRefreshToken(request);
        ResponseCookie cookie = setTokenToCookie(JwtRule.REFRESH_PREFIX.getValue(), refreshToken, REFRESH_EXPIRATION / 1000);
        response.addHeader(JwtRule.JWT_ISSUE_HEADER.getValue(), cookie.toString());

        refreshTokenRepository.findByUserId(request.getId())
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(refreshToken)
                .userId(request.getId())
                .build();

        refreshTokenRepository.save(newRefreshToken);

        return refreshToken;
    }

    public ResponseCookie setTokenToCookie(String tokenPrefix, String token , long maxAgeSeconds){
        return ResponseCookie.from(tokenPrefix, token)
                .path("/")
                .maxAge(maxAgeSeconds)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
    }

    public boolean validateAccessToken(String token){
        return jwtUtil.getTokenStatus(token, jwtUtil.getSigningKey(JwtUtil.tokenType.ACCESS));
    }

    public boolean validateRefreshToken(String token, Long userId){
        boolean isRefreshValid = jwtUtil.getTokenStatus(token, jwtUtil.getSigningKey(JwtUtil.tokenType.REFRESH));

        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new ErrorException(ErrorStatus.INVALID_TOKEN));

        boolean isTokenMatched = Objects.equals(storedToken.getToken(), token);

        return isRefreshValid && isTokenMatched;
    }

    public String resolveTokenFromCookie(HttpServletRequest request, JwtRule tokenPrefix){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return jwtUtil.resolveTokenFromCookie(cookies, tokenPrefix);
    }

    public Authentication getAuthentication(String token){
        UserDetails principal = customUserDetailsService.loadUserByUsername(getUserSubject(token, jwtUtil.getSigningKey(JwtUtil.tokenType.ACCESS)));
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    // 토큰의 subject(유저 email)을 반환
    public String getUserSubject(String token, Key secretKey){

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // accessToken을 identifier로 하여 유저 반환
    public User getUserFromAccess(String accessToken) {

        System.out.println(accessToken);

        String email = getUserSubject(accessToken, jwtUtil.getSigningKey(JwtUtil.tokenType.ACCESS));

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));
    }

    public void logout(User request, HttpServletResponse response) {

        refreshTokenRepository.deleteByUserId(request.getId());

        Cookie accessCookie = jwtUtil.resetToken(JwtRule.ACCESS_PREFIX);
        Cookie refreshCookie = jwtUtil.resetToken(JwtRule.REFRESH_PREFIX);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}
