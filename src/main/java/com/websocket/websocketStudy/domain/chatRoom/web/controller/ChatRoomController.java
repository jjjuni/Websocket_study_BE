package com.websocket.websocketStudy.domain.chatRoom.web.controller;

import com.websocket.websocketStudy.apiPayload.ApiResponse;
import com.websocket.websocketStudy.config.security.OAuth2.UserPrincipal;
import com.websocket.websocketStudy.domain.chatRoom.converter.ChatRoomConverter;
import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.service.ChatRoomService;
import com.websocket.websocketStudy.domain.chatRoom.web.dto.ChatRoomRequestDTO;
import com.websocket.websocketStudy.domain.chatRoom.web.dto.ChatRoomResponseDTO;
import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final UserService userService;

    @Operation(summary = "채팅방 정보 조회 API", description = "채팅방 정보를 조회하는 API (RoomName)")
    @GetMapping("/{room-uuid}")
    public ApiResponse<ChatRoomResponseDTO.ChatRoomInfoDTO> getChatRoomInfo(@PathVariable(name = "room-uuid") UUID roomUuid) {
        ChatRoom chatRoom = chatRoomService.findByUuid(roomUuid);

        return ApiResponse.onSuccess(ChatRoomConverter.toChatRoomInfo(chatRoom));
    }

    @Operation(summary = "유저 채팅방 목록 조회 API", description = "유저 채팅방 목록을 조회하는 API (RoomName, UUID)")
    @GetMapping("")
    public ApiResponse<List<ChatRoomResponseDTO.UserChatRoomDTO>> getUserChatRoomList(@AuthenticationPrincipal UserPrincipal userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());

        List<ChatRoom> chatRoomList = chatRoomService.getUserChatRoomList(user);

        return ApiResponse.onSuccess(ChatRoomConverter.toUserChatRoomListDTO(chatRoomList));
    }

    @Operation(summary = "채팅방 생성 API", description = "채팅방을 생성하는 API")
    @PostMapping("")
    public ApiResponse<ChatRoomResponseDTO.CreateChatRoomDTO> createChatRoom(@RequestBody ChatRoomRequestDTO.CreateChatRoomDTO request, @AuthenticationPrincipal UserPrincipal userDetails) {

        String email = userDetails.getUsername();

        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getRoomName(), email);

        return ApiResponse.onSuccess(ChatRoomConverter.toCreateChatRoomDTO(chatRoom));
    }

    @Operation(summary = "채팅방 참가 API", description = "채팅방에 참가하는 API")
    @PostMapping("/{room-uuid}")
    public ApiResponse<ChatRoomResponseDTO.JoinChatRoomDTO> joinChatRoom(@PathVariable(name = "room-uuid") UUID roomUuid, @AuthenticationPrincipal UserPrincipal userDetails) {

        String email = userDetails.getUsername();

        UserChatRoom userChatRoom = chatRoomService.joinChatRoom(roomUuid, email);

        return ApiResponse.onSuccess(ChatRoomConverter.toJoinChatRoomDTO(userChatRoom.getChatRoom()));
    }

    @Operation(summary = "채팅방 나가기 API", description = "채팅방을 나가는 API")
    @DeleteMapping("/{room-uuid}")
    public ApiResponse<ChatRoomResponseDTO.ExitChatRoomDTO> exitChatRoom(@PathVariable(name = "room-uuid") UUID roomUuid, @AuthenticationPrincipal UserPrincipal userDetails) {

        String email = userDetails.getUsername();

        chatRoomService.exitChatRoom(roomUuid, email);

        return ApiResponse.onSuccess(ChatRoomResponseDTO.ExitChatRoomDTO.builder().exitTime(LocalDateTime.now()).build());
    }
}
