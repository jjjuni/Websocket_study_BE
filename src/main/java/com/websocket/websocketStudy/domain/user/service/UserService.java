package com.websocket.websocketStudy.domain.user.service;

import com.websocket.websocketStudy.domain.user.data.User;

public interface UserService {
    public User findByEmail(String email);
}
