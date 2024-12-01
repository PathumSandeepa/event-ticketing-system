package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String LOG_FILE = "src/main/resources/loggers.txt";
    private static BufferedWriter writer;

    static {
        try {
            // create file and write file in append mode
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized void log(String message) {
        // Print to terminal message
        System.out.println(message);

        // Write to log file
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write log message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}