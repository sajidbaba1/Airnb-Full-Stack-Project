package com.josh.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Long id;
    private Long flightId;
    private String flightNumber;
    private Long aircraftId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String operatingDays;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
