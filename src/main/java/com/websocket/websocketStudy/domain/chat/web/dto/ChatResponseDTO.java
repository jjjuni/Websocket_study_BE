package com.websocket.websocketStudy.domain.chat.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ChatResponseDTO {

    @Getter
    @Builder
    public static class SendMessageDTO {
        String sender;
        String email;
        String message;
        LocalDateTime sendAt;
    }

    @Getter
    @Builder
    public static class ChatListDTO {
        List<ChatDTO> chatList;
        Integer listSize;
        Integer totalPage;
        Integer currentPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Getter
    @Builder
    public static class ChatDTO {
        String sender;
        String email;
        String message;
        LocalDateTime sendAt;
    }
}
