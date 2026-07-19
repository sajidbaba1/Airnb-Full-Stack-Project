package com.josh.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class AirportRequestDTO {

    @NotBlank(message = "IATA code is required")
    @Size(min = 3, max = 4, message = "IATA code must be 3 or 4 characters")
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    private String name;

    private String timeZoneId;

    @NotNull(message = "City ID is required")
    private Long cityId;

    private String address;
    private Double latitude;
    private Double longitude;
}
