package com.josh.flight.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * FlightInstance Entity maps to the "FLIGHT_INSTANCES" table.
 * 
 * It represents actual physical flight events occurring on specific calendar dates.
 * Booking records reference specific FlightInstance records.
 */
@Entity
@Table(name = "FLIGHT_INSTANCES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTANCE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLIGHT_ID", nullable = false)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID", nullable = false)
    private FlightSchedule schedule;

    /**
     * Logical ID referencing the Aircraft in Airline Service.
     */
    @NotNull(message = "Aircraft ID is required")
    @Column(name = "AIRCRAFT_ID", nullable = false)
    private Long aircraftId;

    @NotNull(message = "Flight date is required")
    @Column(name = "FLIGHT_DATE", nullable = false)
    private LocalDate date;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private FlightStatus status;

    @NotNull(message = "Base price is required")
    @Column(name = "BASE_PRICE", nullable = false)
    private Double basePrice;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = FlightStatus.SCHEDULED; // Default state
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
