package com.websocket.websocketStudy.domain.chat.web.dto;

import lombok.Builder;
import lombok.Getter;

public class ChatRequestDTO {

    @Getter
    @Builder
    public static class receiveDTO {
        String message;
        String sender;
        String email;
        String roomId;
    }
}
