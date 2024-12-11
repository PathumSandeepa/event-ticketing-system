// ConfigurationController.java
package com.backend.ticketing_system.controller;

import com.backend.ticketing_system.cli.CliService;
import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// This class represents the REST controller for handling configuration-related requests in the ticketing system
//oop concepts used: abstraction, encapsulation
@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    // The ConfigurationService instance to handle configuration-related operations
    @Autowired
    private ConfigurationService configurationService;

    // The CliService instance to handle CLI operations for the ticketing system (start/stop)
    @Autowired
    private CliService cliService;

    // This method handles the creation of a new configuration in the system
    @PostMapping
    public ResponseEntity<?> createConfiguration(@Valid @RequestBody Configuration configuration) {
        try {
            Configuration savedConfig = configurationService.saveConfiguration(configuration);
            return ResponseEntity.ok(savedConfig);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // This method handles the start of the ticketing system
    @GetMapping("/yes")
    public ResponseEntity<?> startSystem() {
        cliService.startSystem();
        return ResponseEntity.ok("Ticket system started.");
    }

    // This method handles the stopping of the ticketing system
    @GetMapping("/no")
    public ResponseEntity<?> stopSystem() {
        cliService.stopSystem();
        return ResponseEntity.ok("Ticket system stopped.");
    }
}