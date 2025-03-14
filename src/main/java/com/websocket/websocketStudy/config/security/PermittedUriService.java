package com.websocket.websocketStudy.config.security;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PermittedUriService {

    public static final String[] PERMITTED_URI = {
            "/oauth2/**", "/api/auth/**",
            "/login", "/login/oauth2/**", "/signup", "/auth/callback",
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**"};


    public boolean isPermittedURI(String requestURI){
        return Arrays.stream(PERMITTED_URI)
                .anyMatch(permitted -> {
                    String replace = permitted.replace("*", "");
                    return requestURI.contains(replace) || replace.contains(requestURI);
                });
    }
}
