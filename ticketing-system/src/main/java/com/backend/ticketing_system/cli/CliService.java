package com.backend.ticketing_system.cli;

import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CliService {

    @Autowired
    private ConfigurationService configurationService;

    public void startSystem() {
        Configuration configuration = configurationService.getConfiguration();
        if (configuration == null) {
            Logger.log("No configuration found in the database.");
            return;
        }

        // Start the ticket system with the database configuration
        Main.startTicketSystem(configuration);
    }

    public void stopSystem() {
        // Implement stop logic if needed
        Logger.log("Ticket system stopped.");
    }
}