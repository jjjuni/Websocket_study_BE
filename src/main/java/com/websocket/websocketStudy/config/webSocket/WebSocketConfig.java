package com.websocket.websocketStudy.config.webSocket;

import com.websocket.websocketStudy.config.security.interceptor.CustomHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat") // 클라이언트가 연결할 엔드포인트
                .addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOrigins(
                        "http://192.168.50.186:3000",
                        "http://localhost:3000",
                        "https://q-at.store",
                        "https://www.q-at.store"
                ) // CORS 허용 (모든 도메인에서 접근 가능)
                .withSockJS(); // SockJS 지원 (WebSocket을 지원하지 않는 환경에서 대체)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 구독할 경로 설정
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 경로
    }
}