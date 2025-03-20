package com.websocket.websocketStudy.domain.test.web.dto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class TestResponseDTO {

    @Getter
    @Builder
    public static class TestDTO {
        LocalDateTime now;
    }
}
