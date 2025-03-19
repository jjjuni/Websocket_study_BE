package com.websocket.websocketStudy.domain.chatRoom.service;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.user.data.User;

import java.util.List;
import java.util.UUID;

public interface ChatRoomService {
    public ChatRoom createChatRoom(String roomName, String email);
    public UserChatRoom joinChatRoom(UUID roomUuid, String email);
    public ChatRoom findByUuid(UUID roomUuid);
    public List<ChatRoom> getUserChatRoomList(User user);
    public void exitChatRoom(UUID roomUuid, String email);
}
