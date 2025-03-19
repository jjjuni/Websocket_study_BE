package com.websocket.websocketStudy.domain.chatRoom.service;

import com.websocket.websocketStudy.apiPayload.code.status.ErrorStatus;
import com.websocket.websocketStudy.apiPayload.exception.ErrorException;
import com.websocket.websocketStudy.domain.chat.repository.ChatRepository;
import com.websocket.websocketStudy.domain.chatRoom.data.ChatRoom;
import com.websocket.websocketStudy.domain.chatRoom.repository.ChatRoomRepository;
import com.websocket.websocketStudy.domain.chatRoom.repository.UserChatRoomRepository;
import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatRepository chatRepository;

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

        boolean isUserInChatRoom = userChatRoomRepository.existsByUserAndChatRoom(user, chatRoom);

        if (isUserInChatRoom) {
            throw new ErrorException(ErrorStatus.USER_ALREADY_IN_ROOM);
        }

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();

        return userChatRoomRepository.save(userChatRoom);
    }

    @Override
    public ChatRoom findByUuid(UUID roomUuid) {
        return chatRoomRepository.findByRoomUuid(roomUuid)
                .orElseThrow(() -> new ErrorException(ErrorStatus.ROOM_NOT_FOUND));
    }

    @Override
    public List<ChatRoom> getUserChatRoomList(User user) {
        return userChatRoomRepository.findChatRoomsByUserId(user.getId());
    }

    @Override
    public void exitChatRoom(UUID roomUuid, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findByRoomUuid(roomUuid).orElseThrow(() -> new ErrorException(ErrorStatus.ROOM_NOT_FOUND));

        if (!userChatRoomRepository.existsByUserAndChatRoom(user, chatRoom)){
            System.out.println("참여 중이 아님");
            throw new ErrorException(ErrorStatus.USER_NOT_IN_ROOM);
        }
        userChatRoomRepository.deleteByRoomUuidAndUserId(roomUuid, email);

        if (!userChatRoomRepository.existsByChatRoomId(chatRoom.getId())) {
            System.out.println("채팅방 삭제");
            chatRoomRepository.deleteByRoomUuid(roomUuid);
            chatRepository.deleteByRoomUuid(roomUuid.toString());
        }
    }
}
