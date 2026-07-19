package com.josh.location.dto;

import jakarta.validation.constraints.NotBlank;
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
public class CityRequestDTO {

    @NotBlank(message = "City name is required")
    private String name;

    @NotBlank(message = "City code is required")
    @Size(min = 3, max = 5, message = "City code must be between 3 and 5 characters")
    private String cityCode;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2 or 3 characters")
    private String countryCode;

    @NotBlank(message = "Country name is required")
    private String countryName;

    private String timeZoneId;
}
