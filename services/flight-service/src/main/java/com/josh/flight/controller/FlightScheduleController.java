package com.josh.flight.controller;

import com.josh.flight.dto.ScheduleRequestDTO;
import com.josh.flight.dto.ScheduleResponseDTO;
import com.josh.flight.service.FlightScheduleService;
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
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class FlightScheduleController {

    private final FlightScheduleService scheduleService;

    /**
     * Creates a new recurring flight schedule and automatically generates all physical flight 
     * instances for operating weekdays inside the date range.
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody ScheduleRequestDTO dto) {
        ScheduleResponseDTO response = scheduleService.createSchedule(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByFlight(flightId));
    }
}
