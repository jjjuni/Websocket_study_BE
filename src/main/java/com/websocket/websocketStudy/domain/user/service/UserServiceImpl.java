package com.websocket.websocketStudy.domain.user.service;

import com.websocket.websocketStudy.apiPayload.code.status.ErrorStatus;
import com.websocket.websocketStudy.apiPayload.exception.ErrorException;
import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));
    }
}
