package com.josh.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "Flight instance ID is required")
    private Long flightInstanceId;

    @NotNull(message = "Passenger user ID is required")
    private Long passengerId;

    @NotNull(message = "Total booking cost is required")
    private Double totalAmount;

    @NotEmpty(message = "Passenger manifest cannot be empty")
    @Valid
    private List<PassengerDTO> passengers;
}
