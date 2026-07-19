package com.josh.flight.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.flight.dto.FlightRequestDTO;
import com.josh.flight.dto.FlightResponseDTO;
import com.josh.flight.entity.Flight;
import com.josh.flight.mapper.FlightMapper;
import com.josh.flight.repository.FlightRepository;
import com.josh.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    @Transactional
    public FlightResponseDTO createFlight(FlightRequestDTO dto) {
        log.info("Creating flight route: {}", dto.getFlightNumber());
        
        // Check if flight number already registered
        if (flightRepository.findByFlightNumber(dto.getFlightNumber().toUpperCase().trim()).isPresent()) {
            log.error("Failed to create flight. Flight number {} already exists", dto.getFlightNumber());
            throw new IllegalArgumentException("Flight number already exists: " + dto.getFlightNumber());
        }

        Flight flight = FlightMapper.toEntity(dto);
        Flight saved = flightRepository.save(flight);
        
        log.info("Successfully registered flight route with ID: {}", saved.getId());
        return FlightMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightResponseDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));
        return FlightMapper.toDTO(flight);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDTO> getFlightsByRoute(Long airlineId, Long departureAirportId, Long arrivalAirportId) {
        log.info("Querying flight routes for airline {} from departure: {} to arrival: {}", 
                airlineId, departureAirportId, arrivalAirportId);
        
        // IMPORTANT DEBUG CHECK (Avoiding the parameters mismatch bug):
        // Ensure airlineId is passed to the first parameter of the JPA query method.
        List<Flight> flights = flightRepository.findByAirlineIdAndDepartureAirportIdAndArrivalAirportId(
                airlineId, 
                departureAirportId, 
                arrivalAirportId
        );

        return flights.stream()
                .map(FlightMapper::toDTO)
                .collect(Collectors.toList());
    }
}
