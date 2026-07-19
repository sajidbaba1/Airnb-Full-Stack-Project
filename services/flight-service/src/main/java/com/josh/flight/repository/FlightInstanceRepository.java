package com.josh.flight.repository;

import com.josh.flight.entity.FlightInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {
    List<FlightInstance> findByFlightId(Long flightId);
    List<FlightInstance> findByFlightIdAndDate(Long flightId, LocalDate date);
    boolean existsByFlightIdAndDate(Long flightId, LocalDate date);
}
