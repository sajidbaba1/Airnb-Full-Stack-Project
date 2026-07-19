package com.josh.airline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * AircraftResponseDTO represents the structured aircraft details returned by endpoints.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftResponseDTO {
    private Long id;
    private String model;
    private String manufacturer;
    private Integer capacity;
    private String leftSeatColumns;
    private String rightSeatColumns;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
