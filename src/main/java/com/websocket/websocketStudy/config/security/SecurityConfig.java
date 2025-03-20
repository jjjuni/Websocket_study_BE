package com.websocket.websocketStudy.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketStudy.config.security.OAuth2.CustomOAuth2UserService;
import com.websocket.websocketStudy.config.security.OAuth2.filter.ExceptionHandlerFilter;
import com.websocket.websocketStudy.config.security.OAuth2.filter.JwtAuthenticationFilter;
import com.websocket.websocketStudy.config.security.OAuth2.handler.OAuth2FailureHandler;
import com.websocket.websocketStudy.config.security.OAuth2.handler.OAuth2SuccessHandler;
import com.websocket.websocketStudy.domain.token.JwtUtil;
import com.websocket.websocketStudy.domain.token.service.JwtService;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private final PermittedUriService permittedUriService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8080",
                "http://192.168.50.186:3000",
                "http://192.168.50.186:8080",
                "https://q-at.vercel.app"
        ));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(new JwtAuthenticationFilter(permittedUriService, jwtService, jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(objectMapper), JwtAuthenticationFilter.class)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)  // csrf 보호 비활성화
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        // 스웨거는 권한 없이 접근 가능하도록 설정
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                        // 특정 권한이 있어야만 특정 API에 접근할 수 있도록 설정
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 특정 API들은 별도의 인증/인가 과정 없이도 접근이 가능하도록 설정
                        .requestMatchers(PermittedUriService.PERMITTED_URI).permitAll()

                        .anyRequest().permitAll())

                .sessionManagement(configure -> configure
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(customConfigure -> customConfigure
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                        .userInfoEndpoint(endpointConfig -> endpointConfig.userService(customOAuth2UserService))
                );

        return httpSecurity.build();
    }
}
