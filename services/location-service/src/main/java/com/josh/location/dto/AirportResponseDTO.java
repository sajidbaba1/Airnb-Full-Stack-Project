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
public class AirportResponseDTO {
    private Long id;
    private String iataCode;
    private String name;
    private String timeZoneId;
    private Long cityId;
    private String cityCode;
    private String cityName;
    private String address;
    private Double latitude;
    private Double longitude;
}
