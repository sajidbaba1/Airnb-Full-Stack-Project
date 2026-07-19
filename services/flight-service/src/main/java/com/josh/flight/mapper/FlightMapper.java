package com.josh.flight.mapper;

import com.josh.flight.dto.FlightRequestDTO;
import com.josh.flight.dto.FlightResponseDTO;
import com.josh.flight.entity.Flight;

public class FlightMapper {

    public static Flight toEntity(FlightRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Flight.builder()
                .flightNumber(dto.getFlightNumber().toUpperCase().trim())
                .departureAirportId(dto.getDepartureAirportId())
                .arrivalAirportId(dto.getArrivalAirportId())
                .airlineId(dto.getAirlineId())
                .build();
    }

    public static FlightResponseDTO toDTO(Flight entity) {
        if (entity == null) {
            return null;
        }
        return FlightResponseDTO.builder()
                .id(entity.getId())
                .flightNumber(entity.getFlightNumber())
                .departureAirportId(entity.getDepartureAirportId())
                .arrivalAirportId(entity.getArrivalAirportId())
                .airlineId(entity.getAirlineId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
