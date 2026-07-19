package com.josh.flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDTO {

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Departure airport ID is required")
    private Long departureAirportId;

    @NotNull(message = "Arrival airport ID is required")
    private Long arrivalAirportId;

    @NotNull(message = "Airline ID is required")
    private Long airlineId;
}
