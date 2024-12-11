package com.backend.ticketing_system.cli;

import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CliService {

    @Autowired
    private ConfigurationService configurationService;

    private Thread mainThread;

    public void startSystem() {
        Configuration configuration = configurationService.getConfiguration();
        if (configuration == null) {
            Logger.log("No configuration found in the database.");
            return;
        }

        mainThread = new Thread(() -> Main.startTicketSystem(configuration));
        mainThread.start();
    }

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