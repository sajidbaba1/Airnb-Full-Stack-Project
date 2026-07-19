package com.josh.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private Long airlineId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
