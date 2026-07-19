package com.josh.flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Aircraft ID is required")
    private Long aircraftId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalTime arrivalTime;

    /**
     * Comma-separated days of week, e.g. "MONDAY,WEDNESDAY"
     */
    @NotBlank(message = "Operating days are required")
    private String operatingDays;

    @NotNull(message = "Base price is required")
    private Double basePrice;
}
