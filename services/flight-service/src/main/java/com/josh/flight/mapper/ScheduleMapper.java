package com.josh.flight.mapper;

import com.josh.flight.dto.ScheduleRequestDTO;
import com.josh.flight.dto.ScheduleResponseDTO;
import com.josh.flight.entity.Flight;
import com.josh.flight.entity.FlightSchedule;

public class ScheduleMapper {

    public static FlightSchedule toEntity(ScheduleRequestDTO dto, Flight flight) {
        if (dto == null) {
            return null;
        }
        return FlightSchedule.builder()
                .flight(flight)
                .aircraftId(dto.getAircraftId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .operatingDays(dto.getOperatingDays().toUpperCase().trim())
                .active(true)
                .build();
    }

    public static ScheduleResponseDTO toDTO(FlightSchedule entity) {
        if (entity == null) {
            return null;
        }
        
        Long flightId = null;
        String flightNumber = null;
        if (entity.getFlight() != null) {
            flightId = entity.getFlight().getId();
            flightNumber = entity.getFlight().getFlightNumber();
        }

        return ScheduleResponseDTO.builder()
                .id(entity.getId())
                .flightId(flightId)
                .flightNumber(flightNumber)
                .aircraftId(entity.getAircraftId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .departureTime(entity.getDepartureTime())
                .arrivalTime(entity.getArrivalTime())
                .operatingDays(entity.getOperatingDays())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
