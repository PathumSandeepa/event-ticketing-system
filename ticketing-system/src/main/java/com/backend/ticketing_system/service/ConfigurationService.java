package com.backend.ticketing_system.service;

import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

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

    public Configuration getConfiguration() {
        return configurationRepository.findFirstByOrderByIdAsc().orElse(null);
    }
}