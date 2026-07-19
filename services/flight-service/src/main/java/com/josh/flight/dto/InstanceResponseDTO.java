package com.josh.flight.dto;

import com.josh.flight.entity.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstanceResponseDTO {
    private Long id;
    private Long flightId;
    private String flightNumber;
    private Long scheduleId;
    private Long aircraftId;
    private LocalDate date;
    private FlightStatus status;
    private Double basePrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
