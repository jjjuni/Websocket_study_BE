package com.websocket.websocketStudy.domain.chat.web.controller;

import com.websocket.websocketStudy.domain.chat.converter.ChatConverter;
import com.websocket.websocketStudy.domain.chat.service.ChatService;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;

    @MessageMapping("/sendMessage/{room-uuid}") // 클라이언트가 보낼 경로: "/app/sendMessage/{roomId}"
    @SendTo("/topic/{room-uuid}")
    public ChatResponseDTO.SendMessageDTO receiveAndSendMessage(@RequestBody ChatRequestDTO.ReceiveDTO chatContentRequest, SimpMessageHeaderAccessor headerAccessor) {

        // CustomHandshakeInterceptor에서 설정한 roomUuid 가져오기
        String roomUuid = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("roomUuid");

        // CustomHandshakeInterceptor에서 설정한 유저 정보 가져오기
        String email = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("email");

        // 받은 메시지 정보(roomId, message), 유저 정보를 합쳐 receiveAndSave에 전달
        chatService.receiveAndSave(roomUuid, chatContentRequest, email);

        System.out.println("방 ID : " + email + " | " + chatContentRequest.getMessage());

        return ChatConverter.toSendMessageDTO(chatContentRequest, email);
    }
}