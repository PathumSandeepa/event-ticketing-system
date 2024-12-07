// ConfigurationController.java
package com.backend.ticketing_system.controller;

import com.backend.ticketing_system.cli.CliService;
import com.backend.ticketing_system.model.Configuration;
import com.backend.ticketing_system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private CliService cliService;

    @PostMapping
    public ResponseEntity<?> createConfiguration(@Valid @RequestBody Configuration configuration) {
        try {
            Configuration savedConfig = configurationService.saveConfiguration(configuration);
            return ResponseEntity.ok(savedConfig);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/yes")
    public ResponseEntity<?> startSystem() {
        cliService.startSystem();
        return ResponseEntity.ok("Ticket system started.");
    }

    @GetMapping("/no")
    public ResponseEntity<?> stopSystem() {
        cliService.stopSystem();
        return ResponseEntity.ok("Ticket system stopped.");
    }
}