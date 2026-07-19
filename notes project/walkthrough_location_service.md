# Walkthrough - Location Microservice Implementation

This document summarizes the changes made during the implementation of the **Location Microservice** (Cities & Airports).

---

## 1. Directory Structure Established

The following files were created under `services/location-service/src/main/java/com/josh/location/`:

### Database Entities
*   **City.java**: Maps to `CITIES` table with unique constraint on `CITY_CODE`.
*   **Airport.java**: Maps to `AIRPORTS` table with a lazy-loaded `ManyToOne` relationship mapping to `City`.

### Database Repositories
*   **CityRepository.java**: Extends `JpaRepository` with `findByCityCode()`.
*   **AirportRepository.java**: Extends `JpaRepository` with `findByIataCode()`.

### DTOs & Mappers
*   **CityRequestDTO.java** & **CityResponseDTO.java**: Clean API payload structures.
*   **CityMapper.java**: Mapper mapping entity to DTO.
*   **AirportRequestDTO.java** & **AirportResponseDTO.java**: API payload structures.
*   **AirportMapper.java**: Maps Airport entity to DTO, exposing parent city properties.

### Business Service Layer (with Redis Caching)
*   **CityService.java** & **CityServiceImpl.java**: Implements caching (`@Cacheable` on lookups, `@CacheEvict` on updates/bulk creations) and database queries.
*   **AirportService.java** & **AirportServiceImpl.java**: Implements Airport business transactions with parent city validation and caching.

### REST API Controllers
*   **CityController.java**: Exposes REST endpoints (`GET`, `POST`, `POST /bulk`) under `/api/cities`.
*   **AirportController.java**: Exposes REST endpoints (`GET`, `POST`, `POST /bulk`) under `/api/airports`.

---

## 2. Caching & Persistence Configurations
*   Caching is fully enabled on reference lookup functions, backed by standard Redis settings in `application.yml`.
*   JPA schema DDL generation is set to update for schema synchronization.
