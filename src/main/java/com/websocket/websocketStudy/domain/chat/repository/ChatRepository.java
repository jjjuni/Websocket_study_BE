package com.websocket.websocketStudy.domain.chat.repository;

import com.websocket.websocketStudy.domain.chat.data.ChatContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatContent, String> {

    Page<ChatContent> findAllByRoomUuid(String roomId, PageRequest pageRequest);

    default Page<ChatContent> findAllByRoomIdDesc(String roomId, int page, int size) {
        // PageRequest를 생성할 때 Sort.by(Sort.Order.desc())를 사용하여 내림차순 정렬
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("sendAt")));  // "sendAt" 필드를 기준으로 내림차순 정렬
        return findAllByRoomUuid(roomId, pageRequest);
    }

    @Query("{ 'roomUuid': ?0 }")
    void deleteByRoomUuid(String roomUuid);
}
