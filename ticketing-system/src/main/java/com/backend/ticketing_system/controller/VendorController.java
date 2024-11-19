package com.backend.ticketing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.backend.ticketing_system.dto.VendorRegistrationDto;
import com.backend.ticketing_system.dto.VendorLoginDto;
import com.backend.ticketing_system.model.Vendor;
import com.backend.ticketing_system.service.VendorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendors")
@Validated
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerVendor(@Valid @RequestBody VendorRegistrationDto registrationDto) {
        Vendor existingVendor = vendorService.authenticateVendor(registrationDto.getEmail(), registrationDto.getPassword());
        if (existingVendor != null) {
            return ResponseEntity.badRequest().body("Vendor already exists");
        }
        vendorService.registerVendor(registrationDto);
        return ResponseEntity.ok("Vendor registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateVendor(@Valid @RequestBody VendorLoginDto loginDto) {
        Vendor vendor = vendorService.authenticateVendor(loginDto.getEmail(), loginDto.getPassword());
        if (vendor != null) {
            return ResponseEntity.ok("Vendor authenticated successfully");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}