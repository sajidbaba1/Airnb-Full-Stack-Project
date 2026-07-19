package com.josh.location.mapper;

import com.josh.location.dto.CityRequestDTO;
import com.josh.location.dto.CityResponseDTO;
import com.josh.location.entity.City;

public class CityMapper {

    public static City toEntity(CityRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return City.builder()
                .name(dto.getName())
                .cityCode(dto.getCityCode().toUpperCase())
                .countryCode(dto.getCountryCode().toUpperCase())
                .countryName(dto.getCountryName())
                .timeZoneId(dto.getTimeZoneId())
                .build();
    }

    public static CityResponseDTO toDTO(City entity) {
        if (entity == null) {
            return null;
        }
        return CityResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cityCode(entity.getCityCode())
                .countryCode(entity.getCountryCode())
                .countryName(entity.getCountryName())
                .timeZoneId(entity.getTimeZoneId())
                .build();
    }
}
