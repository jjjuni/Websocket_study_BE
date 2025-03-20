package com.websocket.websocketStudy.domain.test.web.controller;


import com.websocket.websocketStudy.apiPayload.ApiResponse;import com.websocket.websocketStudy.domain.test.web.dto.TestResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/test")
public class TestContoller {
    @Operation(summary = "테스트 API", description = "테스트")
    @GetMapping("")
    public ApiResponse<TestResponseDTO.TestDTO> getChatList() {
        return ApiResponse.onSuccess(TestResponseDTO.TestDTO.builder().now(LocalDateTime.now()).build());
    }
}
