package com.backend.ticketing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.ticketing_system.dto.CustomerRegistrationDto;
import com.backend.ticketing_system.model.Customer;
import com.backend.ticketing_system.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Customer registerCustomer(CustomerRegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setName(registrationDto.getName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhone(registrationDto.getPhone());
        customer.setAddress(registrationDto.getAddress());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public Customer authenticateCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null && passwordEncoder.matches(password, customer.getPassword())) {
            return customer;
        }
        return null;
    }
}