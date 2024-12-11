package com.backend.ticketing_system.service;

import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// This class provides service methods for handling configuration-related operations in the ticketing system
//oop concepts used: abstraction, encapsulation
@Service
public class ConfigurationService {

    // The ConfigurationRepository instance to handle configuration-related database operations
    @Autowired
    private ConfigurationRepository configurationRepository;

    // This method saves the configuration in the database and validates the input data
    public Configuration saveConfiguration(Configuration configuration) throws Exception {
        if (configuration.getMaxTicketCapacity() <= configuration.getTotalTickets()) {
            throw new Exception("Max ticket capacity should be greater than total tickets.");
        }

        // Check if a configuration already exists
        Optional<Configuration> existingConfig = configurationRepository.findFirstByOrderByIdAsc();
        if (existingConfig.isPresent()) {
            configuration.setId(existingConfig.get().getId());
        }

        return configurationRepository.save(configuration);
    }
    // This method retrieves the configuration from the database and returns it
    public Configuration getConfiguration() {
        return configurationRepository.findFirstByOrderByIdAsc().orElse(null);
    }
}