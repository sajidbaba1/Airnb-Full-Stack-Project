package com.josh.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main Entry Point for the Booking Microservice.
 * 
 * 1. @SpringBootApplication: Bootstraps local components.
 * 2. @EnableDiscoveryClient: Registers instance with Eureka.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BookingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }
}
