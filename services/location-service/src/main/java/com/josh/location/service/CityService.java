package com.josh.location.service;

import com.josh.location.dto.CityRequestDTO;
import com.josh.location.dto.CityResponseDTO;

import java.util.List;

public interface CityService {
    CityResponseDTO getCityById(Long id);
    CityResponseDTO getCityByCode(String cityCode);
    CityResponseDTO createCity(CityRequestDTO dto);
    List<CityResponseDTO> getAllCities();
    List<CityResponseDTO> bulkCreateCities(List<CityRequestDTO> dtos);
}
