package com.backend.ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.ticketing_system.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}