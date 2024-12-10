package com.backend.ticketing_system.cli;

import com.backend.ticketing_system.websocket.LogWebSocketHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String LOG_FILE = "src/main/resources/loggers.txt";
    private static BufferedWriter writer;

    static {
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized void log(String message) {
        System.out.println(message);

        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write log message: " + e.getMessage());
            e.printStackTrace();
        }

        // Send log message to WebSocket clients if running in Spring Boot mode
        if (isSpringBootMode()) {
            LogWebSocketHandler.broadcast(message);
        }
    }

    private static boolean isSpringBootMode() {
        // Implement logic to check if running in Spring Boot mode
        return true; // Placeholder, replace with actual logic
    }
}