package com.websocket.websocketStudy.domain.chatRoom.repository;

import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.user.data.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    boolean existsByUserAndChatRoom(User user, ChatRoom chatRoom);

    boolean existsByChatRoomId(Long chatRoomId);

    @Query("SELECT ucr.chatRoom FROM UserChatRoom ucr WHERE ucr.user.id = :userId")
    List<ChatRoom> findChatRoomsByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserChatRoom u WHERE u.chatRoom.roomUuid = :roomUuid AND u.user.email = :email")
    void deleteByRoomUuidAndUserId(UUID roomUuid, String email);
}
