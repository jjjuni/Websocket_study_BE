package com.websocket.websocketStudy.domain.user.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    UNKNOWN("ROLE_UNKNOWN");

    private final String key;
}
