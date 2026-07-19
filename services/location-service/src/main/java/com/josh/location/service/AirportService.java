package com.josh.location.service;

import com.josh.location.dto.AirportRequestDTO;
import com.josh.location.dto.AirportResponseDTO;

import java.util.List;

public interface AirportService {
    AirportResponseDTO getAirportById(Long id);
    AirportResponseDTO getAirportByIataCode(String iataCode);
    AirportResponseDTO createAirport(AirportRequestDTO dto);
    List<AirportResponseDTO> getAllAirports();
    List<AirportResponseDTO> bulkCreateAirports(List<AirportRequestDTO> dtos);
}
