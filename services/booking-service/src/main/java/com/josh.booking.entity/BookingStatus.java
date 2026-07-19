package com.josh.booking.entity;

/**
 * BookingStatus Enum represents the life-cycle of a booking transaction.
 */
public enum BookingStatus {
    /**
     * Initial reservation made, awaiting payment processing confirmation.
     */
    PENDING,

    /**
     * Seat reservation completed successfully.
     */
    CONFIRMED,

    /**
     * Reservation revoked either manually by the customer or due to payment timeout.
     */
    CANCELLED
}
