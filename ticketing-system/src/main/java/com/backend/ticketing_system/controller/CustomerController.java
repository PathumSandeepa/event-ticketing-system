package com.backend.ticketing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.backend.ticketing_system.dto.CustomerRegistrationDto;
import com.backend.ticketing_system.dto.CustomerLoginDto;
import com.backend.ticketing_system.model.Customer;
import com.backend.ticketing_system.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        Customer existingCustomer = customerService.authenticateCustomer(registrationDto.getEmail(), registrationDto.getPassword());
        if (existingCustomer != null) {
            return ResponseEntity.badRequest().body("Customer already exists");
        }
        customerService.registerCustomer(registrationDto);
        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateCustomer(@Valid @RequestBody CustomerLoginDto loginDto) {
        Customer customer = customerService.authenticateCustomer(loginDto.getEmail(), loginDto.getPassword());
        if (customer != null) {
            return ResponseEntity.ok("Customer authenticated successfully");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}