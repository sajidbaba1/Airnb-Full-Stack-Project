package com.josh.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main Entry Point for the Airline Core Microservice.
 * 
 * 1. @SpringBootApplication: Bootstraps the spring context.
 * 2. @EnableDiscoveryClient: Registers this service instance with the Eureka Discovery Server registry.
 * 3. @EnableCaching: Enables Spring's annotation-driven cache management, enabling Redis support.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class AirlineServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirlineServiceApplication.class, args);
    }
}
