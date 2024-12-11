package com.backend.ticketing_system.cli;

import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

// This class is responsible for starting and stopping the ticket system.
// oop concepts used: Encapsulation, Inheritance, Polymorphism
@Service
public class CliService {

    // Dependency injection of ConfigurationService class
    @Autowired
    private ConfigurationService configurationService;

    private Thread mainThread;

    // This method starts the ticket system by getting the configuration from the database and starting the ticket system.
    public void startSystem() {
        Configuration configuration = configurationService.getConfiguration();
        if (configuration == null) {
            Logger.log("No configuration found in the database.");
            return;
        }

        // Starting the ticket system in a separate thread.
        // This is done so that the main thread can continue to run and the ticket system can run in the background.
        mainThread = new Thread(() -> Main.startTicketSystem(configuration));
        mainThread.start();
    }

    // This method stops the ticket system by setting the isRunning flag to false.
    // This will stop the ticket system from running.
    // This method uses reflection to access the isRunning flag in the Main class.
    public void stopSystem() {
        try {
            Field isRunningField = Main.class.getDeclaredField("isRunning");
            isRunningField.setAccessible(true);
            AtomicBoolean isRunning = (AtomicBoolean) isRunningField.get(null);
            isRunning.set(false);
            Logger.log("Ticket system is stopping.");
        } catch (Exception e) {
            Logger.log("Failed to stop the ticket system: " + e.getMessage());
        }
    }
}