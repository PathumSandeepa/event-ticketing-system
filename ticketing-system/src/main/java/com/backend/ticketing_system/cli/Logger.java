package com.backend.ticketing_system.cli;

import com.backend.ticketing_system.websocket.LogWebSocketHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//oop concepts used: encapsulation, static block, singleton
public class Logger {
    // The log file path
    private static final String LOG_FILE = "src/main/resources/loggers.txt";
    private static BufferedWriter writer;

    // This static block initializes the BufferedWriter object
    // It will be executed when the class is loaded
    static {
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // This method logs the message to the console and writes it to the log file
    // It also sends the log message to WebSocket clients if running in Spring Boot mode
    // The method is synchronized to ensure thread safety when writing to the log file
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
        // The LogWebSocketHandler class is used to broadcast messages to all clients
        if (isSpringBootMode()) {
            LogWebSocketHandler.broadcast(message);
        }
    }

    // This method checks if the application is running in Spring Boot mode
    private static boolean isSpringBootMode() {
        return true;
    }
}