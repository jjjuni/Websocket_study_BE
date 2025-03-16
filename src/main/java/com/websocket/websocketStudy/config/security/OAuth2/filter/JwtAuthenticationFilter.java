package com.websocket.websocketStudy.config.security.OAuth2.filter;

import com.websocket.websocketStudy.apiPayload.code.status.ErrorStatus;
import com.websocket.websocketStudy.apiPayload.exception.ErrorException;
import com.websocket.websocketStudy.config.security.PermittedUriService;
import com.websocket.websocketStudy.domain.token.JwtUtil;
import com.websocket.websocketStudy.domain.token.data.enums.JwtRule;
import com.websocket.websocketStudy.domain.token.service.JwtService;
import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final PermittedUriService permittedUriService;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Value("${spring.jwt.access-expiration}")
    private long ACCESS_EXPIRATION;

    @Value("${spring.jwt.refresh-expiration}")
    private long REFRESH_EXPIRATION;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //         인증이 필요 없는 uri 처리
        if (permittedUriService.isPermittedURI(request.getRequestURI())){
            System.out.println(request.getRequestURI() + " : 허가 필요 없음");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);
        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);

        // 토큰이 둘 다 없을 경우 TOKEN_NOT_FOUND throw
        if (Objects.equals(accessToken, "") && Objects.equals(refreshToken, "")) {
            System.out.println("TOKEN_NOT_FOUND");
            response.addCookie(jwtUtil.resetToken(JwtRule.ACCESS_PREFIX));
            response.addCookie(jwtUtil.resetToken(JwtRule.REFRESH_PREFIX));
            throw new ErrorException(ErrorStatus.TOKEN_NOT_FOUND);
        }

        try {
            // accessToken 만 없을 경우 EXPIRED_ACCESS_TOKEN throw
            if (Objects.equals(accessToken, "")) {
                System.out.println("EXPIRED_ACCESS_TOKEN");

                User user = findUserByRefreshToken(refreshToken);

                if (jwtService.validateRefreshToken(refreshToken, user.getId())) {
                    // accessToken 재발급
                    accessToken = jwtService.generateAccessToken(response, user);

                    // 쿠키에 accessToken 추가 (단, controller에서 바로 사용은 불가능. 다음 요청부터 accessToken이 들어 있는 쿠키로 요청이 오는 것!)
                    jwtService.setTokenToCookie(JwtRule.ACCESS_PREFIX.getValue(), accessToken, ACCESS_EXPIRATION);

                    // SecurityContextHolder에 유저 정보 등록
                    SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(accessToken));

                } else {
                    throw new ErrorException(ErrorStatus.INVALID_TOKEN);
                }
            }

            if (jwtService.validateAccessToken(accessToken)){
                System.out.println(request.getRequestURI() + " : 유저 정보 확인");
                setAuthenticationToContext(accessToken);
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            // 잘못된 토큰 처리
            System.out.println("INVALID_TOKEN");
            // 혹시 모르니 쿠키에 있는 모든 토큰 초기화
            response.addCookie(jwtUtil.resetToken(JwtRule.ACCESS_PREFIX));
            response.addCookie(jwtUtil.resetToken(JwtRule.REFRESH_PREFIX));
            throw new ErrorException(ErrorStatus.INVALID_TOKEN);
        }
    }

    private User findUserByRefreshToken(String refreshToken) {
        String identifier = jwtService.getUserSubject(refreshToken, jwtUtil.getSigningKey(JwtUtil.tokenType.REFRESH));
        System.out.println(identifier);
        return userRepository.findByEmail(identifier).orElseThrow(() -> new ErrorException(ErrorStatus.TOKEN_NOT_FOUND));
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
