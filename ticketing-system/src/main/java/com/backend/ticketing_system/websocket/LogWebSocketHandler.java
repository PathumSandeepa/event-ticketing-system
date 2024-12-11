package com.backend.ticketing_system.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// This class handles WebSocket requests for logging messages to connected clients
//oop concepts used: abstraction, encapsulation
public class LogWebSocketHandler extends TextWebSocketHandler {

    // This set stores all active WebSocket sessions for broadcasting messages
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    // This method is called when a new WebSocket connection is established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
    // This method is called when a new WebSocket connection is closed and removes the session from the active sessions set
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
    // This method broadcasts a message to all active WebSocket sessions by sending a TextMessage
    public static void broadcast(String message) {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}