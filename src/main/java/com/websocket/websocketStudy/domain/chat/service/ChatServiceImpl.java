package com.websocket.websocketStudy.domain.chat.service;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.repository.ChatRepository;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;

    @Override
    public ChatContent receiveAndSave(ChatRequestDTO.receiveDTO request) {
        ChatContent chatContent = ChatContent.builder()
                .sender(request.getSender())
                .message(request.getMessage())
                .roomId(request.getRoomId())
                .sendAt(LocalDateTime.now())
                .email(request.getEmail())
                .build();

        return chatRepository.save(chatContent);
    }
}
