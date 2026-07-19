package com.josh.flight.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * FlightSchedule Entity maps to the "FLIGHT_SCHEDULES" table.
 * 
 * It stores scheduling rules defining *how often* and *when* a specific flight route 
 * operates between start and end dates.
 */
@Entity
@Table(name = "FLIGHT_SCHEDULES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLIGHT_ID", nullable = false)
    private Flight flight;

    /**
     * Logical ID referencing the Aircraft in Airline Service.
     */
    @NotNull(message = "Aircraft ID is required")
    @Column(name = "AIRCRAFT_ID", nullable = false)
    private Long aircraftId;

    @NotNull(message = "Start date is required")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Departure time is required")
    @Column(name = "DEPARTURE_TIME", nullable = false)
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Column(name = "ARRIVAL_TIME", nullable = false)
    private LocalTime arrivalTime;

    /**
     * Stores comma-separated weekdays on which this flight operates.
     * E.g., "MONDAY,WEDNESDAY,FRIDAY"
     */
    @NotBlank(message = "Operating days are required")
    @Column(name = "OPERATING_DAYS", nullable = false)
    private String operatingDays;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active = true;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
