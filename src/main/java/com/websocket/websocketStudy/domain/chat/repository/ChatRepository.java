package com.websocket.websocketStudy.domain.chat.repository;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatContent, String> {
    ChatContent findChatContentBySender(String sender);
}
