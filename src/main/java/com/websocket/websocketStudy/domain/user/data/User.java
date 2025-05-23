package com.websocket.websocketStudy.domain.user.data;

import com.websocket.websocketStudy.domain.user.data.enums.Role;
import com.websocket.websocketStudy.domain.user.data.enums.SocialType;
import com.websocket.websocketStudy.domain.chatRoom.data.mapping.UserChatRoom;
import com.websocket.websocketStudy.global.data.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 40)
    private String email;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserChatRoom> userChatRoomList = new ArrayList<>();
}