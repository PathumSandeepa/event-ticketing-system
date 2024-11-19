package com.backend.ticketing_system.service;

import com.backend.ticketing_system.dto.CustomerRegistrationDto;
import com.backend.ticketing_system.model.Customer;

public interface CustomerService {
    Customer registerCustomer(CustomerRegistrationDto registrationDto);
    Customer authenticateCustomer(String email, String password);
}