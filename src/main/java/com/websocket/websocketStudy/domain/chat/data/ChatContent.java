package com.websocket.websocketStudy.domain.chat.data;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages") // 실제 몽고 DB 컬렉션 이름
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatContent {
    @Id
    private String id; // 또는 ObjectId
    private String sender;
    private String email;
    private String message;
    private String roomUuid;
    private LocalDateTime sendAt;
}