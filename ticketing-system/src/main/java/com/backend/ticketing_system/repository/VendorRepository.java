package com.backend.ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.ticketing_system.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByEmail(String email);
}