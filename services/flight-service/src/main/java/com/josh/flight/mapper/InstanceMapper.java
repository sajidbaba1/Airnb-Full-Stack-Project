package com.josh.flight.mapper;

import com.josh.flight.dto.InstanceResponseDTO;
import com.josh.flight.entity.FlightInstance;

public class InstanceMapper {

    public static InstanceResponseDTO toDTO(FlightInstance entity) {
        if (entity == null) {
            return null;
        }

        Long flightId = null;
        String flightNumber = null;
        if (entity.getFlight() != null) {
            flightId = entity.getFlight().getId();
            flightNumber = entity.getFlight().getFlightNumber();
        }

        Long scheduleId = null;
        if (entity.getSchedule() != null) {
            scheduleId = entity.getSchedule().getId();
        }

        return InstanceResponseDTO.builder()
                .id(entity.getId())
                .flightId(flightId)
                .flightNumber(flightNumber)
                .scheduleId(scheduleId)
                .aircraftId(entity.getAircraftId())
                .date(entity.getDate())
                .status(entity.getStatus())
                .basePrice(entity.getBasePrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
