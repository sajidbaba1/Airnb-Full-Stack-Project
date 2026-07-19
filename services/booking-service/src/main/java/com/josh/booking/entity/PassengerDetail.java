package com.josh.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PassengerDetail Entity maps to the "PASSENGER_DETAILS" table.
 * 
 * It stores personal profiles and seat allocations of manifest members.
 */
@Entity
@Table(name = "PASSENGER_DETAILS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PASSENGER_DETAIL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_ID", nullable = false)
    private Booking booking;

    @NotBlank(message = "Passenger name is required")
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank(message = "Passenger email is required")
    @Email(message = "Invalid email format")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NotNull(message = "Passenger age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Column(name = "AGE", nullable = false)
    private Integer age;

    @NotBlank(message = "Passenger gender is required")
    @Column(name = "GENDER", nullable = false)
    private String gender;

    /**
     * Assigned seat row and column combination, e.g. "12A".
     */
    @NotBlank(message = "Assigned seat is required")
    @Column(name = "ASSIGNED_SEAT", nullable = false)
    private String assignedSeat;
}
