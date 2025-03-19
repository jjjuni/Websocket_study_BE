package com.websocket.websocketStudy.domain.chatRoom.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChatRoomResponseDTO {

    @Getter
    @Builder
    public static class ChatRoomInfoDTO {
        String roomName;
    }

    @Getter
    @Builder
    public static class UserChatRoomDTO {
        UUID roomUuid;
        String roomName;
    }

    @Getter
    @Builder
    public static class CreateChatRoomDTO {
        UUID roomUuid;
        String roomName;
    }

    @Getter
    @Builder
    public static class JoinChatRoomDTO {
        UUID roomUuid;
        String roomName;
    }

    @Getter
    @Builder
    public static class ExitChatRoomDTO {
        LocalDateTime exitTime;
    }
}
