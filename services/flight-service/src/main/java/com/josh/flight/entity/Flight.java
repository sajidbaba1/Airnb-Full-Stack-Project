package com.josh.flight.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Flight Entity maps to the "FLIGHTS" table in the database.
 * 
 * It models static route coordinates (flight number, departing airport, and arriving airport).
 * 
 * --- MICROSERVICES DISTRIBUTED DESIGN NOTE: ---
 * In a monolith, departureAirportId and arrivalAirportId would be foreign keys referencing 
 * the AIRPORTS table. In a microservices architecture, because the Location Service owns the 
 * AIRPORTS table, we only store logical ID references here. There are NO hard foreign key 
 * database constraints. This prevents database sharing and ensures absolute service boundaries.
 */
@Entity
@Table(name = "FLIGHTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLIGHT_ID")
    private Long id;

    @NotBlank(message = "Flight number is required")
    @Column(name = "FLIGHT_NUMBER", unique = true, nullable = false)
    private String flightNumber;

    /**
     * Logical ID referencing the departing Airport in Location Service.
     */
    @NotNull(message = "Departure airport ID is required")
    @Column(name = "DEPARTURE_AIRPORT_ID", nullable = false)
    private Long departureAirportId;

    /**
     * Logical ID referencing the arriving Airport in Location Service.
     */
    @NotNull(message = "Arrival airport ID is required")
    @Column(name = "ARRIVAL_AIRPORT_ID", nullable = false)
    private Long arrivalAirportId;

    /**
     * Logical ID referencing the Airline in Airline Core Service.
     */
    @NotNull(message = "Airline ID is required")
    @Column(name = "AIRLINE_ID", nullable = false)
    private Long airlineId;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
