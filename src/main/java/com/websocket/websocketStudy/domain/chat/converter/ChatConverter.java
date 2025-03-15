package com.websocket.websocketStudy.domain.chat.converter;

import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatResponseDTO;

import java.time.LocalDateTime;

public class ChatConverter {

    public static ChatResponseDTO.sendMessageDTO toSendMessageDTO(ChatRequestDTO.receiveDTO request) {
        return ChatResponseDTO.sendMessageDTO.builder()
                .sender(request.getSender())
                .email(request.getEmail())
                .message(request.getMessage())
                .sendAt(LocalDateTime.now())
                .build();
    }
}
