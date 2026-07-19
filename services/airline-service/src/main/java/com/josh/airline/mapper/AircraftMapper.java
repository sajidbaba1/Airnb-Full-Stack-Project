package com.josh.airline.mapper;

import com.josh.airline.dto.AircraftRequestDTO;
import com.josh.airline.dto.AircraftResponseDTO;
import com.josh.airline.entity.Aircraft;

/**
 * AircraftMapper handles conversions between Aircraft entity and DTO objects.
 */
public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Aircraft.builder()
                .model(dto.getModel())
                .manufacturer(dto.getManufacturer())
                .capacity(dto.getCapacity())
                .leftSeatColumns(dto.getLeftSeatColumns().toUpperCase().trim())
                .rightSeatColumns(dto.getRightSeatColumns().toUpperCase().trim())
                .build();
    }

    public static AircraftResponseDTO toDTO(Aircraft entity) {
        if (entity == null) {
            return null;
        }
        return AircraftResponseDTO.builder()
                .id(entity.getId())
                .model(entity.getModel())
                .manufacturer(entity.getManufacturer())
                .capacity(entity.getCapacity())
                .leftSeatColumns(entity.getLeftSeatColumns())
                .rightSeatColumns(entity.getRightSeatColumns())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
