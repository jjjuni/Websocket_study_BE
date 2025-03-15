package com.websocket.websocketStudy.domain.user.repository;

import com.websocket.websocketStudy.domain.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
