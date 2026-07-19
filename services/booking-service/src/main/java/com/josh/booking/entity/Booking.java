package com.josh.booking.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Booking Entity maps to the "BOOKINGS" table.
 * 
 * It manages the header details of flight reservations.
 */
@Entity
@Table(name = "BOOKINGS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_ID")
    private Long id;

    /**
     * Unique alphanumeric reference number, e.g., "BK-E4B2F5"
     */
    @NotBlank(message = "Booking number is required")
    @Column(name = "BOOKING_NUMBER", unique = true, nullable = false)
    private String bookingNumber;

    /**
     * Logical ID referencing the physical FlightInstance in Flight Service.
     */
    @NotNull(message = "Flight instance ID is required")
    @Column(name = "FLIGHT_INSTANCE_ID", nullable = false)
    private Long flightInstanceId;

    /**
     * Logical ID referencing the User in User Service who initiated the transaction.
     */
    @NotNull(message = "Passenger user ID is required")
    @Column(name = "PASSENGER_ID", nullable = false)
    private Long passengerId;

    @NotNull(message = "Total booking cost is required")
    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Double totalAmount;

    @NotNull(message = "Booking status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private BookingStatus status;

    @Column(name = "BOOKING_DATE", nullable = false, updatable = false)
    private LocalDateTime bookingDate;

    /**
     * Set of passenger details attached to this booking.
     * 
     * CascadeType.ALL + orphanRemoval = true: Saves, updates, or deletes 
     * passenger records automatically whenever they are modified inside this list.
     */
    @Builder.Default
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassengerDetail> passengers = new ArrayList<>();

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.bookingDate = LocalDateTime.now();
        this.status = BookingStatus.PENDING; // Default state
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Helper utility to bidirectionally link passenger manifest details.
     */
    public void addPassenger(PassengerDetail passenger) {
        passengers.add(passenger);
        passenger.setBooking(this);
    }
}
