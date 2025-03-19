package com.websocket.websocketStudy.domain.chatRoom.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class ChatRoomRequestDTO {

    @Getter
    public static class CreateChatRoomDTO {
        String roomName;
    }

    @Getter
    public static class JoinChatRoomDTO {
        UUID roomUuid;
    }
}
