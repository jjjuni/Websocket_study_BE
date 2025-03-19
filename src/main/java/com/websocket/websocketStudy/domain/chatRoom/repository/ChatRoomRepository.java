package com.websocket.websocketStudy.domain.chatRoom.repository;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomUuid(UUID roomUuid);

    @Transactional
    @Modifying
    @Query("DELETE FROM ChatRoom c WHERE c.roomUuid = :roomUuid")
    void deleteByRoomUuid(UUID roomUuid);
}
