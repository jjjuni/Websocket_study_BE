package com.websocket.websocketStudy.domain.chat.service;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.repository.ChatRepository;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;

    @Override
    public ChatContent receiveAndSave(String roomUuid, ChatRequestDTO.ReceiveDTO request, String email) {

        ChatContent chatContent = ChatContent.builder()
                .sender(request.getSender())
                .message(request.getMessage())
                .roomUuid(roomUuid)
                .sendAt(LocalDateTime.now())
                .email(email)
                .build();

        return chatRepository.save(chatContent);
    }

    @Override
    public Page<ChatContent> getChatContentList(String roomId, Integer page) {

        return chatRepository.findAllByRoomIdDesc(roomId, page, 10);
    }
}
