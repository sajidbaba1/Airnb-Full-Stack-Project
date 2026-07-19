package com.josh.airline.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Aircraft Entity maps to the "AIRCRAFT" table in the database.
 * 
 * It manages fleet information, manufacturer profiles, and seating layouts.
 */
@Entity
@Table(name = "AIRCRAFT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AIRCRAFT_ID")
    private Long id;

    @NotBlank(message = "Aircraft model is required")
    @Column(name = "MODEL", nullable = false)
    private String model;

    @NotBlank(message = "Manufacturer name is required")
    @Column(name = "MANUFACTURER", nullable = false)
    private String manufacturer;

    /**
     * Total physical seat capacity.
     * @Min ensures that we don't register invalid aircraft profiles with zero or negative capacities.
     */
    @NotNull(message = "Total capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 seat")
    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    /**
     * Represents the seat columns configuration on the left side of the aisle.
     * For example, "ABC" represents 3 seats on the left.
     */
    @NotBlank(message = "Left seat columns configuration is required")
    @Column(name = "LEFT_SEAT_COLUMNS", nullable = false)
    private String leftSeatColumns;

    /**
     * Represents the seat columns configuration on the right side of the aisle.
     * For example, "DEF" represents 3 seats on the right (yielding a standard 3-3 cabin configuration: A-B-C | D-E-F).
     */
    @NotBlank(message = "Right seat columns configuration is required")
    @Column(name = "RIGHT_SEAT_COLUMNS", nullable = false)
    private String rightSeatColumns;

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
