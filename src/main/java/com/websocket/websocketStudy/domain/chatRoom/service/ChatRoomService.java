package com.websocket.websocketStudy.domain.chatRoom.service;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;

import java.util.UUID;

public interface ChatRoomService {
    public ChatRoom createChatRoom(String roomName, String email);
    public UserChatRoom joinChatRoom(UUID roomUuid, String email);
}
