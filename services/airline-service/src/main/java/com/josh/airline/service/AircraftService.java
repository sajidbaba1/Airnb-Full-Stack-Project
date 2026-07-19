package com.josh.airline.service;

import com.josh.airline.dto.AircraftRequestDTO;
import com.josh.airline.dto.AircraftResponseDTO;

import java.util.List;

public interface AircraftService {
    AircraftResponseDTO createAircraft(AircraftRequestDTO dto);
    AircraftResponseDTO getAircraftById(Long id);
    List<AircraftResponseDTO> getAllAircraft();
}
