package com.josh.location.mapper;

import com.josh.location.dto.AirportRequestDTO;
import com.josh.location.dto.AirportResponseDTO;
import com.josh.location.entity.Airport;
import com.josh.location.entity.City;

public class AirportMapper {

    public static Airport toEntity(AirportRequestDTO dto, City city) {
        if (dto == null) {
            return null;
        }
        return Airport.builder()
                .iataCode(dto.getIataCode().toUpperCase())
                .name(dto.getName())
                .timeZoneId(dto.getTimeZoneId())
                .city(city)
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }

    public static AirportResponseDTO toDTO(Airport entity) {
        if (entity == null) {
            return null;
        }
        
        Long cityId = null;
        String cityCode = null;
        String cityName = null;
        
        if (entity.getCity() != null) {
            cityId = entity.getCity().getId();
            cityCode = entity.getCity().getCityCode();
            cityName = entity.getCity().getName();
        }

        return AirportResponseDTO.builder()
                .id(entity.getId())
                .iataCode(entity.getIataCode())
                .name(entity.getName())
                .timeZoneId(entity.getTimeZoneId())
                .cityId(cityId)
                .cityCode(cityCode)
                .cityName(cityName)
                .address(entity.getAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
