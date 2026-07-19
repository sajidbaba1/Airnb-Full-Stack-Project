package com.josh.flight.repository;

import com.josh.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    /**
     * Finds flights operating on a specific route for an airline.
     * 
     * Note: Avoid the parameter mapping mismatch bug here! The first parameter matches airlineId, 
     * NOT arrivalAirportId.
     */
    List<Flight> findByAirlineIdAndDepartureAirportIdAndArrivalAirportId(
            Long airlineId, 
            Long departureAirportId, 
            Long arrivalAirportId
    );
}
