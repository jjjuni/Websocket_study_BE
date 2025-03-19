package com.websocket.websocketStudy.domain.chatRoom.converter;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.web.dto.ChatRoomResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomConverter {

    public static ChatRoomResponseDTO.ChatRoomInfoDTO toChatRoomInfo(ChatRoom chatRoom) {
        return ChatRoomResponseDTO.ChatRoomInfoDTO.builder()
                .roomName(chatRoom.getRoomName())
                .build();
    }

    public static List<ChatRoomResponseDTO.UserChatRoomDTO> toUserChatRoomListDTO(List<ChatRoom> chatRoomList) {
        return chatRoomList.stream()
                .map(chatRoom ->
                        ChatRoomResponseDTO.UserChatRoomDTO.builder()
                                .roomUuid(chatRoom.getRoomUuid())
                                .roomName(chatRoom.getRoomName())
                                .build()
                ).collect(Collectors.toList());
    }

    public static ChatRoomResponseDTO.CreateChatRoomDTO toCreateChatRoomDTO(ChatRoom chatRoom){
        return ChatRoomResponseDTO.CreateChatRoomDTO.builder()
                .roomUuid(chatRoom.getRoomUuid())
                .roomName(chatRoom.getRoomName())
                .build();
    }

    public static ChatRoomResponseDTO.JoinChatRoomDTO toJoinChatRoomDTO (ChatRoom chatRoom) {
        return ChatRoomResponseDTO.JoinChatRoomDTO.builder()
                .roomUuid(chatRoom.getRoomUuid())
                .roomName(chatRoom.getRoomName())
                .build();
    }
}
