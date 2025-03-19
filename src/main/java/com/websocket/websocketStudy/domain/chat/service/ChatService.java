package com.websocket.websocketStudy.domain.chat.service;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import org.springframework.data.domain.Page;

public interface ChatService {
    public ChatContent receiveAndSave(String roomUuid, ChatRequestDTO.ReceiveDTO request, String email);
    public Page<ChatContent> getChatContentList(String roomId, Integer page);
    
}
