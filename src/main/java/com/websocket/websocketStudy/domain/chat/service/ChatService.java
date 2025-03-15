package com.websocket.websocketStudy.domain.chat.service;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;

public interface ChatService {
    public ChatContent receiveAndSave(ChatRequestDTO.receiveDTO request);
}
