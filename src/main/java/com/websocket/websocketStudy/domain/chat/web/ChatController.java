package com.websocket.websocketStudy.domain.chat.web;

import com.websocket.websocketStudy.domain.chat.data.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage/{roomId}") // 클라이언트가 보낼 경로: "/app/sendMessage/{roomId}"
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        System.out.println("방 ID : " + chatMessage.getRoomId() + " | " + chatMessage.getContent());
        return chatMessage;
    }
}
