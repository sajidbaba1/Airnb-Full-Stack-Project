package com.josh.flight.controller;

import com.josh.flight.dto.InstanceResponseDTO;
import com.josh.flight.entity.FlightStatus;
import com.josh.flight.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/instances")
@RequiredArgsConstructor
public class FlightInstanceController {

    private final FlightInstanceService instanceService;

    @GetMapping("/{id}")
    public ResponseEntity<InstanceResponseDTO> getInstanceById(@PathVariable Long id) {
        return ResponseEntity.ok(instanceService.getInstanceById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<InstanceResponseDTO>> getInstancesByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(instanceService.getInstancesByFlight(flightId));
    }

    @GetMapping("/flight/{flightId}/search")
    public ResponseEntity<List<InstanceResponseDTO>> getInstancesByFlightAndDate(
            @PathVariable Long flightId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<InstanceResponseDTO> response = instanceService.getInstancesByFlightAndDate(flightId, date);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InstanceResponseDTO> updateInstanceStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status
    ) {
        InstanceResponseDTO response = instanceService.updateInstanceStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
