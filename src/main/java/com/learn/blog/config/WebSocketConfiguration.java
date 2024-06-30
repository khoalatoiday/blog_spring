package com.learn.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // khai báo url để kết nối websocket server qua handshake
        registry.addEndpoint("/url-for-connect-websocket").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // những message mà được gửi về mà bắt đầu bằng "/app" thì sẽ qua @MessageMapping trong @Controller
        config.setApplicationDestinationPrefixes("/app");
        // những message mà được gửi về mà bắt đầu bằng "/topic" hoặc "queue" thì sẽ qua những các built-in message
        // broker "topic" và "queue" để subscriptions và broadcasting
        config.enableSimpleBroker("/topic", "/queue");
    }
}
