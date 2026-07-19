package com.josh.airline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AircraftRequestDTO represents payload parameters for registering a new aircraft fleet.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftRequestDTO {

    @NotBlank(message = "Aircraft model is required")
    private String model;

    @NotBlank(message = "Manufacturer name is required")
    private String manufacturer;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 seat")
    private Integer capacity;

    /**
     * Regex check ensuring columns configuration contains only uppercase alphabets (A-Z).
     * For example, ABC is valid, but AB3 or A-B-C is invalid.
     */
    @NotBlank(message = "Left seat columns are required")
    @Pattern(regexp = "^[A-Z]+$", message = "Left seat columns must contain uppercase letters only (e.g. ABC)")
    private String leftSeatColumns;

    @NotBlank(message = "Right seat columns are required")
    @Pattern(regexp = "^[A-Z]+$", message = "Right seat columns must contain uppercase letters only (e.g. DEF)")
    private String rightSeatColumns;
}
