package com.websocket.websocketStudy.domain.chat.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class ChatRequestDTO {

    @Getter
    @Builder
    public static class ReceiveDTO {
        String message;
        String sender;
    }
}
