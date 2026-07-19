package com.josh.location.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.location.dto.CityRequestDTO;
import com.josh.location.dto.CityResponseDTO;
import com.josh.location.entity.City;
import com.josh.location.mapper.CityMapper;
import com.josh.location.repository.CityRepository;
import com.josh.location.service.CityService;
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
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    @Cacheable(value = "cities", key = "#id")
    @Transactional(readOnly = true)
    public CityResponseDTO getCityById(Long id) {
        log.info("Fetching city by ID from database: {}", id);
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with ID: " + id));
        return CityMapper.toDTO(city);
    }

    @Override
    @Cacheable(value = "cities", key = "#cityCode")
    @Transactional(readOnly = true)
    public CityResponseDTO getCityByCode(String cityCode) {
        log.info("Fetching city by code from database: {}", cityCode);
        City city = cityRepository.findByCityCode(cityCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("City not found with code: " + cityCode));
        return CityMapper.toDTO(city);
    }

    @Override
    @CacheEvict(value = "cities", allEntries = true)
    @Transactional
    public CityResponseDTO createCity(CityRequestDTO dto) {
        log.info("Creating new city in database: {}", dto.getCityCode());
        City city = CityMapper.toEntity(dto);
        City saved = cityRepository.save(city);
        return CityMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDTO> getAllCities() {
        log.info("Fetching all cities from database");
        return cityRepository.findAll().stream()
                .map(CityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "cities", allEntries = true)
    @Transactional
    public List<CityResponseDTO> bulkCreateCities(List<CityRequestDTO> dtos) {
        log.info("Bulk importing {} cities", dtos.size());
        List<CityResponseDTO> results = new ArrayList<>();
        for (CityRequestDTO dto : dtos) {
            try {
                results.add(createCity(dto));
            } catch (Exception e) {
                log.error("Failed to import city: {}. Error: {}", dto.getCityCode(), e.getMessage());
            }
        }
        return results;
    }
}
