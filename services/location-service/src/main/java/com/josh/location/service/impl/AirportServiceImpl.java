package com.josh.location.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.location.dto.AirportRequestDTO;
import com.josh.location.dto.AirportResponseDTO;
import com.josh.location.entity.Airport;
import com.josh.location.entity.City;
import com.josh.location.mapper.AirportMapper;
import com.josh.location.repository.AirportRepository;
import com.josh.location.repository.CityRepository;
import com.josh.location.service.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    @Cacheable(value = "airports", key = "#id")
    @Transactional(readOnly = true)
    public AirportResponseDTO getAirportById(Long id) {
        log.info("Fetching airport by ID from database: {}", id);
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + id));
        return AirportMapper.toDTO(airport);
    }

    @Override
    @Cacheable(value = "airports", key = "#iataCode")
    @Transactional(readOnly = true)
    public AirportResponseDTO getAirportByIataCode(String iataCode) {
        log.info("Fetching airport by IATA code from database: {}", iataCode);
        Airport airport = airportRepository.findByIataCode(iataCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with IATA code: " + iataCode));
        return AirportMapper.toDTO(airport);
    }

    @Override
    @CacheEvict(value = "airports", allEntries = true)
    @Transactional
    public AirportResponseDTO createAirport(AirportRequestDTO dto) {
        log.info("Creating new airport: {} associated with City ID: {}", dto.getIataCode(), dto.getCityId());
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create airport. Parent City ID " + dto.getCityId() + " not found."));
        
        Airport airport = AirportMapper.toEntity(dto, city);
        Airport saved = airportRepository.save(airport);
        return AirportMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportResponseDTO> getAllAirports() {
        log.info("Fetching all airports from database");
        return airportRepository.findAll().stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "airports", allEntries = true)
    @Transactional
    public List<AirportResponseDTO> bulkCreateAirports(List<AirportRequestDTO> dtos) {
        log.info("Bulk importing {} airports", dtos.size());
        List<AirportResponseDTO> results = new ArrayList<>();
        for (AirportRequestDTO dto : dtos) {
            try {
                results.add(createAirport(dto));
            } catch (Exception e) {
                log.error("Failed to import airport: {}. Error: {}", dto.getIataCode(), e.getMessage());
            }
        }
        return results;
    }
}
