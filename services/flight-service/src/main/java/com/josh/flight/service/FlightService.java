package com.josh.flight.service;

import com.josh.flight.dto.FlightRequestDTO;
import com.josh.flight.dto.FlightResponseDTO;

import java.util.List;

public interface FlightService {
    FlightResponseDTO createFlight(FlightRequestDTO dto);
    FlightResponseDTO getFlightById(Long id);
    List<FlightResponseDTO> getFlightsByRoute(Long airlineId, Long departureAirportId, Long arrivalAirportId);
}
