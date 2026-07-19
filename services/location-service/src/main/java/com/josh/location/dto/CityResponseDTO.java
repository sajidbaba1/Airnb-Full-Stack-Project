package com.josh.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponseDTO {
    private Long id;
    private String name;
    private String cityCode;
    private String countryCode;
    private String countryName;
    private String timeZoneId;
}
