package com.josh.flight.entity;

/**
 * FlightStatus Enum represents the operational states of a flight instance.
 */
public enum FlightStatus {
    /**
     * Flight is scheduled to operate as planned.
     */
    SCHEDULED,

    /**
     * Departure has been delayed.
     */
    DELAYED,

    /**
     * Flight has taken off and is currently in the air.
     */
    DEPARTED,

    /**
     * Flight has landed at the destination airport.
     */
    ARRIVED,

    /**
     * Operation cancelled due to technical or weather reasons.
     */
    CANCELLED
}
