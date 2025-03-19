package com.websocket.websocketStudy.domain.chat.converter;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatRequestDTO;
import com.websocket.websocketStudy.domain.chat.web.dto.ChatResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChatConverter {

    public static ChatResponseDTO.SendMessageDTO toSendMessageDTO(ChatRequestDTO.ReceiveDTO request, String email) {
        return ChatResponseDTO.SendMessageDTO.builder()
                .sender(request.getSender())
                .email(email)
                .message(request.getMessage())
                .sendAt(LocalDateTime.now())
                .build();
    }

    public static ChatResponseDTO.ChatDTO tochatDTO(ChatContent chatContent) {
        return ChatResponseDTO.ChatDTO.builder()
                .sender(chatContent.getSender())
                .email(chatContent.getEmail())
                .message(chatContent.getMessage())
                .sendAt(chatContent.getSendAt())
                .build();
    }

    public static ChatResponseDTO.ChatListDTO toChatListDTO(Page<ChatContent> chatContentList, Integer page){

        List<ChatResponseDTO.ChatDTO> chatDTOList = chatContentList.stream()
                .map(ChatConverter::tochatDTO).toList();

        return ChatResponseDTO.ChatListDTO.builder()
                .isLast(chatContentList.isLast())
                .isFirst(chatContentList.isFirst())
                .totalPage(chatContentList.getTotalPages())
                .currentPage(page)
                .totalElements(chatContentList.getTotalElements())
                .listSize(chatContentList.getSize())
                .chatList(chatDTOList)
                .build();
    }
}
