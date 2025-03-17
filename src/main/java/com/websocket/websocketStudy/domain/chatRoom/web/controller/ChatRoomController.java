package com.websocket.websocketStudy.domain.chatRoom.web.controller;

import com.websocket.websocketStudy.apiPayload.ApiResponse;
import com.websocket.websocketStudy.config.security.OAuth2.UserPrincipal;
import com.websocket.websocketStudy.domain.chatRoom.converter.ChatRoomConverter;
import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.service.ChatRoomService;
import com.websocket.websocketStudy.domain.chatRoom.web.dto.ChatRoomResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성 API", description = "채팅방을 생성하는 API")
    @PostMapping("/create/{roomName}")
    public ApiResponse<ChatRoomResponseDTO.CreateChatRoomDTO> createChatRoom(@PathVariable(name = "roomName") String roomName, @AuthenticationPrincipal UserPrincipal userDetails) {

        String email = userDetails.getUsername();

        ChatRoom chatRoom = chatRoomService.createChatRoom(roomName, email);

        return ApiResponse.onSuccess(ChatRoomConverter.toCreateChatRoomDTO(chatRoom));
    }

    @Operation(summary = "채팅방 참가 API", description = "채팅방에 참가하는 API")
    @PostMapping("/join/{roomUuid}")
    public ApiResponse<ChatRoomResponseDTO.JoinChatRoomDTO> joinChatRoom(@PathVariable(name = "roomUuid") UUID roomUuid, @AuthenticationPrincipal UserPrincipal userDetails) {

        String email = userDetails.getUsername();

        UserChatRoom userChatRoom = chatRoomService.joinChatRoom(roomUuid, email);

        return ApiResponse.onSuccess(ChatRoomConverter.toJoinChatRoomDTO(userChatRoom.getChatRoom()));
    }


}
