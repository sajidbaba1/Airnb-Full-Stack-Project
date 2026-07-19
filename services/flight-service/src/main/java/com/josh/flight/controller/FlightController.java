package com.josh.flight.controller;

import com.josh.flight.dto.FlightRequestDTO;
import com.josh.flight.dto.FlightResponseDTO;
import com.josh.flight.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponseDTO> createFlight(@Valid @RequestBody FlightRequestDTO dto) {
        FlightResponseDTO response = flightService.createFlight(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/route")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsByRoute(
            @RequestParam Long airlineId,
            @RequestParam Long departureAirportId,
            @RequestParam Long arrivalAirportId
    ) {
        List<FlightResponseDTO> response = flightService.getFlightsByRoute(airlineId, departureAirportId, arrivalAirportId);
        return ResponseEntity.ok(response);
    }
}
