package com.josh.location.controller;

import com.josh.location.dto.CityRequestDTO;
import com.josh.location.dto.CityResponseDTO;
import com.josh.location.service.CityService;
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
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponseDTO> createCity(@Valid @RequestBody CityRequestDTO dto) {
        CityResponseDTO response = cityService.createCity(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<CityResponseDTO>> bulkCreateCities(@RequestBody List<CityRequestDTO> dtos) {
        List<CityResponseDTO> responses = cityService.bulkCreateCities(dtos);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDTO> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping("/code/{cityCode}")
    public ResponseEntity<CityResponseDTO> getCityByCode(@PathVariable String cityCode) {
        return ResponseEntity.ok(cityService.getCityByCode(cityCode));
    }
}
