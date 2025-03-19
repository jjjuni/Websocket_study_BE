package com.websocket.websocketStudy.domain.chat.web.controller;

import com.websocket.websocketStudy.apiPayload.ApiResponse;
import com.websocket.websocketStudy.domain.chat.converter.ChatConverter;
import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.service.ChatService;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatResponseDTO;
import com.websocket.websocketStudy.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "특정 채팅방의 채팅 목록 조회 API", description = "특정 채팅방의 채팅 목록을 조회하는 API로 페이징을 포함. query String으로 page 번호 필요")
    @GetMapping("/{room-uuid}")
    public ApiResponse<ChatResponseDTO.ChatListDTO> getChatList(@PathVariable(name = "room-uuid") String roomUuid, @CheckPage @RequestParam(name = "page") Integer page) {
        Page<ChatContent> chatContentList = chatService.getChatContentList(roomUuid, page - 1);
        return ApiResponse.onSuccess(ChatConverter.toChatListDTO(chatContentList, page));
    }
}
