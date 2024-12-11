package com.backend.ticketing_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

// This class is the entry point for the Spring Boot application
//oop concepts used: abstraction, encapsulation
@SpringBootApplication
public class RealTimeTicketingSystemBackendApplication {

	// This method starts the Spring Boot application with the specified arguments and configurations
	public static void main(String[] args) {
		SpringApplication.run(RealTimeTicketingSystemBackendApplication.class, args);
	}

	// This method creates a new CorsFilter bean to configure CORS settings for the application to allow requests from the frontend
	// It allows requests from the specified frontend origin "http://localhost:4200" and sets the allowed headers, methods, and credentials
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200")); // Frontend origin
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"
		));
		// Expose the following headers to the frontend for access in the response headers
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin", "Content-Type", "Accept", "Authorization"
		));
		// Allow the following methods for requests from the frontend
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// Create a new UrlBasedCorsConfigurationSource and register the CorsConfiguration for all paths "/**" in the application
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(source);
	}
}
