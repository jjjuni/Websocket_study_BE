package com.websocket.websocketStudy.domain.chat.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ChatResponseDTO {

    @Getter
    @Builder
    public static class sendMessageDTO {
        String sender;
        String email;
        String message;
        LocalDateTime sendAt;
    }
}
