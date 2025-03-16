package com.websocket.websocketStudy.domain.auth.web.controller;

import com.websocket.websocketStudy.apiPayload.ApiResponse;
import com.websocket.websocketStudy.config.security.OAuth2.UserPrincipal;
import com.websocket.websocketStudy.domain.auth.web.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public ApiResponse<AuthResponseDTO.UserInfoDTO> getUserInfo(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserPrincipal userDetails) {
        String email = userDetails.getUsername();

        String name = userDetails.getName();

        return ApiResponse.onSuccess(AuthResponseDTO.UserInfoDTO.builder()
                        .email(email)
                        .name(name)
                        .build()
        );
    }
}
