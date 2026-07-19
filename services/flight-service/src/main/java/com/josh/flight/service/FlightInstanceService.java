package com.josh.flight.service;

import com.josh.flight.dto.InstanceResponseDTO;
import com.josh.flight.entity.FlightStatus;

import java.time.LocalDate;
import java.util.List;

public interface FlightInstanceService {
    InstanceResponseDTO getInstanceById(Long id);
    List<InstanceResponseDTO> getInstancesByFlight(Long flightId);
    List<InstanceResponseDTO> getInstancesByFlightAndDate(Long flightId, LocalDate date);
    InstanceResponseDTO updateInstanceStatus(Long id, FlightStatus status);
}
