package com.websocket.websocketStudy.domain.chatRoom.service;

import com.websocket.websocketStudy.apiPayload.code.status.ErrorStatus;
import com.websocket.websocketStudy.apiPayload.exception.ErrorException;
import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.repository.ChatRoomRepository;
import com.websocket.websocketStudy.domain.chatRoom.repository.UserChatRoomRepository;
import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Override
    public ChatRoom createChatRoom(String roomName, String email){
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomName(roomName)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(newChatRoom);

        joinChatRoom(savedChatRoom.getRoomUuid(), email);

        return savedChatRoom;
    }

    @Override
    public UserChatRoom joinChatRoom(UUID roomUuid, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findByRoomUuid(roomUuid).orElseThrow(() -> new ErrorException(ErrorStatus.ROOM_NOT_FOUND));

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();

        return userChatRoomRepository.save(userChatRoom);
    }
}
