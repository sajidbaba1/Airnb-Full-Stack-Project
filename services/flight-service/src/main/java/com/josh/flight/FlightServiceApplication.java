package com.josh.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main Entry Point for the Flight Operation Microservice.
 * 
 * 1. @SpringBootApplication: Configures local spring framework configurations.
 * 2. @EnableDiscoveryClient: Registers this service instance with the Eureka naming server client registry.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FlightServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightServiceApplication.class, args);
    }
}
