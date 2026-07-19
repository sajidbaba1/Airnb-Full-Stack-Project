package com.josh.airline.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.airline.dto.AircraftRequestDTO;
import com.josh.airline.dto.AircraftResponseDTO;
import com.josh.airline.entity.Aircraft;
import com.josh.airline.mapper.AircraftMapper;
import com.josh.airline.repository.AircraftRepository;
import com.josh.airline.service.AircraftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    @Override
    @CacheEvict(value = "aircrafts", allEntries = true)
    @Transactional
    public AircraftResponseDTO createAircraft(AircraftRequestDTO dto) {
        log.info("Registering new aircraft: {} {}", dto.getManufacturer(), dto.getModel());
        
        Aircraft aircraft = AircraftMapper.toEntity(dto);
        Aircraft saved = aircraftRepository.save(aircraft);
        
        log.info("Successfully registered aircraft with ID: {}", saved.getId());
        return AircraftMapper.toDTO(saved);
    }

    /**
     * Retrieves an aircraft profile.
     * 
     * LEARNING POINT:
     * We annotate lookups with @Cacheable. If a query with the specific ID was previously executed,
     * Spring retrieves the value from Redis cache and returns it, bypassing the database entirely.
     * This drastically reduces database read latency.
     */
    @Override
    @Cacheable(value = "aircrafts", key = "#id")
    @Transactional(readOnly = true)
    public AircraftResponseDTO getAircraftById(Long id) {
        log.info("Fetching aircraft by ID from database: {}", id);
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + id));
        return AircraftMapper.toDTO(aircraft);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AircraftResponseDTO> getAllAircraft() {
        log.info("Fetching all aircraft from database");
        return aircraftRepository.findAll().stream()
                .map(AircraftMapper::toDTO)
                .collect(Collectors.toList());
    }
}
