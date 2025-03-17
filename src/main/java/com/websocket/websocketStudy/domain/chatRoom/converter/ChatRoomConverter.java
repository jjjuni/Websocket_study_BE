package com.websocket.websocketStudy.domain.chatRoom.converter;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.web.dto.ChatRoomResponseDTO;

public class ChatRoomConverter {

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
