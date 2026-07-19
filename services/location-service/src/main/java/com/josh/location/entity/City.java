package com.josh.location.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CITIES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CITY_ID")
    private Long id;

    @NotBlank(message = "City name is required")
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank(message = "City code is required")
    @Column(name = "CITY_CODE", unique = true, nullable = false)
    private String cityCode;

    @NotBlank(message = "Country code is required")
    @Column(name = "COUNTRY_CODE", nullable = false)
    private String countryCode;

    @NotBlank(message = "Country name is required")
    @Column(name = "COUNTRY_NAME", nullable = false)
    private String countryName;

    @Column(name = "TIME_ZONE_ID")
    private String timeZoneId;
}
