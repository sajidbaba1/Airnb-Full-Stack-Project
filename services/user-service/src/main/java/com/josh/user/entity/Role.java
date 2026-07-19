package com.josh.user.entity;

/**
 * Role Enum defines the access privileges of users in the system.
 * 
 * In industry systems, it is standard practice to prefix roles with "ROLE_" 
 * when working with Spring Security, as Spring Security's role-based authorization 
 * (like @PreAuthorize("hasRole('USER')")) implicitly searches for this prefix.
 */
public enum Role {
    /**
     * Standard passenger/customer who can search flights, choose seats, and book tickets.
     */
    ROLE_USER,

    /**
     * Airline operator admin who can schedule flights, manage aircraft fleets, 
     * and customize seat maps for their specific airline.
     */
    ROLE_AIRLINE_ADMIN,

    /**
     * Overall platform system administrator who can ban/suspend airlines, 
     * manage airports, cities, and oversee system metrics.
     */
    ROLE_SYSTEM_ADMIN
}
