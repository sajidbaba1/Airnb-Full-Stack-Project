package com.josh.airline.controller;

import com.josh.airline.dto.AircraftRequestDTO;
import com.josh.airline.dto.AircraftResponseDTO;
import com.josh.airline.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API controller exposing endpoints for fleet and aircraft profiles.
 */
@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    /**
     * Endpoint to register a new aircraft configuration.
     * @Valid ensures incoming arguments comply with validation constraints before executing method.
     */
    @PostMapping
    public ResponseEntity<AircraftResponseDTO> createAircraft(@Valid @RequestBody AircraftRequestDTO dto) {
        AircraftResponseDTO response = aircraftService.createAircraft(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint to list all registered aircraft fleets.
     */
    @GetMapping
    public ResponseEntity<List<AircraftResponseDTO>> getAllAircraft() {
        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    /**
     * Endpoint to retrieve details of a specific aircraft profile by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponseDTO> getAircraftById(@PathVariable Long id) {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }
}
