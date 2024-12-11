package com.backend.ticketing_system.repository;

import com.backend.ticketing_system.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface represents the repository for Configuration entities in the ticketing system database
//oop concepts used: abstraction, encapsulation
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Optional<Configuration> findFirstByOrderByIdAsc();
}