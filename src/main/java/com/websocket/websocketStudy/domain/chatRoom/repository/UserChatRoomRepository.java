package com.websocket.websocketStudy.domain.chatRoom.repository;

import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
}
