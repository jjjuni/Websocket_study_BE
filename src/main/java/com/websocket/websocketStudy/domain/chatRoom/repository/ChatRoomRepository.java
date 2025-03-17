package com.websocket.websocketStudy.domain.chatRoom.repository;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomUuid(UUID roomUuid);
}
