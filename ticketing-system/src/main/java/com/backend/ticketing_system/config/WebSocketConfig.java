package com.backend.ticketing_system.config;

import com.backend.ticketing_system.websocket.LogWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// This class configures the WebSocket settings for the application
//oop concepts used: abstraction, encapsulation
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    // This method registers the LogWebSocketHandler to handle WebSocket requests at the specified endpoint "/api/ws/logs"
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logWebSocketHandler(), "api/ws/logs").setAllowedOrigins("*");
    }

    // This method creates a new instance of the LogWebSocketHandler class
    @Bean
    public LogWebSocketHandler logWebSocketHandler() {
        return new LogWebSocketHandler();
    }
}