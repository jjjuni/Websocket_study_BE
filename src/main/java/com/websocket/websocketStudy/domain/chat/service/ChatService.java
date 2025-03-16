package com.websocket.websocketStudy.domain.chat.service;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import org.springframework.data.domain.Page;

public interface ChatService {
    public ChatContent receiveAndSave(ChatRequestDTO.ReceiveDTO request);
    public Page<ChatContent> getChatContentList(String roomId, Integer page);
}
