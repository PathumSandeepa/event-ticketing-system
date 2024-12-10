package com.backend.ticketing_system.config;

import com.backend.ticketing_system.websocket.LogWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logWebSocketHandler(), "api/ws/logs").setAllowedOrigins("*");
    }

    @Bean
    public LogWebSocketHandler logWebSocketHandler() {
        return new LogWebSocketHandler();
    }
}