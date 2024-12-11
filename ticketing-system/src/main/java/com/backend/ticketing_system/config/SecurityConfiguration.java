package com.backend.ticketing_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// This class configures the security settings for the application
//oop concepts used: abstraction, encapsulation
@org.springframework.context.annotation.Configuration
public class SecurityConfiguration {

    // This method configures the security filter chain to disable CSRF protection and allow all requests without authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll() // Allow all requests without authentication
                );

        return http.build();
    }
}