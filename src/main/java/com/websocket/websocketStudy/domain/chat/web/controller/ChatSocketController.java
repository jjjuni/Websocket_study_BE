package com.websocket.websocketStudy.domain.chat.web.controller;

import com.websocket.websocketStudy.domain.chat.converter.ChatConverter;
import com.websocket.websocketStudy.domain.chat.service.ChatService;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;

    @MessageMapping("/sendMessage/{roomId}") // 클라이언트가 보낼 경로: "/app/sendMessage/{roomId}"
    @SendTo("/topic/{roomId}")
    public ChatResponseDTO.SendMessageDTO receiveAndSendMessage(@RequestBody ChatRequestDTO.ReceiveDTO chatContentRequest) {

        // 받은 메시지 정보(roomId, message), 유저 정보를 합쳐 receiveAndSave에 전달
        chatService.receiveAndSave(chatContentRequest);

        System.out.println("방 ID : " + chatContentRequest.getRoomId() + " | " + chatContentRequest.getMessage());

        return ChatConverter.toSendMessageDTO(chatContentRequest);
    }
}
