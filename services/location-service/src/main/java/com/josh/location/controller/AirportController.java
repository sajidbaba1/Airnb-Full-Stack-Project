package com.josh.location.controller;

import com.josh.location.dto.AirportRequestDTO;
import com.josh.location.dto.AirportResponseDTO;
import com.josh.location.service.AirportService;
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

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponseDTO> createAirport(@Valid @RequestBody AirportRequestDTO dto) {
        AirportResponseDTO response = airportService.createAirport(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<AirportResponseDTO>> bulkCreateAirports(@RequestBody List<AirportRequestDTO> dtos) {
        List<AirportResponseDTO> responses = airportService.bulkCreateAirports(dtos);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AirportResponseDTO>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDTO> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @GetMapping("/iata/{iataCode}")
    public ResponseEntity<AirportResponseDTO> getAirportByIataCode(@PathVariable String iataCode) {
        return ResponseEntity.ok(airportService.getAirportByIataCode(iataCode));
    }
}
