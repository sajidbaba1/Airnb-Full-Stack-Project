# Josh Airline Microservices - Live Development steps Log

This file tracks the exact order of coding steps, configurations, and decisions taken during the development of the **Josh Airline Microservices System**.

---

## Chronological Development steps

### Phase 1: Project Bootstrapping & Multi-Module Setup
1.  **Initialized Parent POM**: Created root [pom.xml](file:///c:/full%20stack%20projects/airnb%20project/pom.xml) to govern project properties, compile versions, and Spring Boot/Cloud dependency management.
2.  **Configured Aggregators**: Created [services/pom.xml](file:///c:/full%20stack%20projects/airnb%20project/services/pom.xml) and [cloud-infrastructure/pom.xml](file:///c:/full%20stack%20projects/airnb%20project/cloud-infrastructure/pom.xml).
3.  **Built Common Library (`common-lib`)**: Created [common-lib/pom.xml](file:///c:/full%20stack%20projects/airnb%20project/common-lib/pom.xml). Implemented:
    *   [ErrorResponse.java](file:///c:/full%20stack%20projects/airnb%20project/common-lib/src/main/java/com/josh/common/dto/ErrorResponse.java) (standardized JSON error payload).
    *   [ResourceNotFoundException.java](file:///c:/full%20stack%20projects/airnb%20project/common-lib/src/main/java/com/josh/common/exception/ResourceNotFoundException.java) (custom runtime exception).
    *   [GlobalExceptionHandler.java](file:///c:/full%20stack%20projects/airnb%20project/common-lib/src/main/java/com/josh/common/exception/GlobalExceptionHandler.java) (global advice controller to map exceptions to the standard error response).
4.  **Configured Registry Server**: Set up Eureka Discovery Server running on port `8761`.

---

### Phase 2: Location Microservice Development
1.  **Created Configuration**: Set up port `5001`, Redis caching hosts, and JPA datasource properties.
2.  **Modeled Entities**:
    *   [City.java](file:///c:/full%20stack%20projects/airnb%20project/services/location-service/src/main/java/com/josh/location/entity/City.java): Represents cities with a unique city code.
    *   [Airport.java](file:///c:/full%20stack%20projects/airnb%20project/services/location-service/src/main/java/com/josh/location/entity/Airport.java): Many-to-One lazy-loaded mapping to parent City.
3.  **Created Repositories**: Declared custom queries in `CityRepository` and `AirportRepository`.
4.  **Created DTOs & Mappers**: Standardized requests/responses and mapper routines to avoid entity leak.
5.  **Implemented Services with Caching**: Integrated `@Cacheable` (cache name `cities`/`airports`) to cache lookup results in Redis, and `@CacheEvict` to clear caches when creating new entries.
6.  **Created Controllers**: Configured endpoints under `/api/cities` and `/api/airports` supporting single and bulk imports.

---

### Phase 3: User Identity & Security Microservice Development
1.  **Created Configuration**: Set up port `5002` and database settings.
2.  **Modeled Security Entities**:
    *   [Role.java](file:///c:/full%20stack%20projects/airnb%20project/services/user-service/src/main/java/com/josh/user/entity/Role.java): Access privileges enum (`ROLE_USER`, `ROLE_AIRLINE_ADMIN`, `ROLE_SYSTEM_ADMIN`).
    *   [User.java](file:///c:/full%20stack%20projects/airnb%20project/services/user-service/src/main/java/com/josh/user/entity/User.java): Hashed credentials profile with database hooks (`@PrePersist` and `@PreUpdate`).
3.  **Built JWT Helper (`JwtUtil`)**: Wrote logic for token creation, subject/role parsing, and validation using HMAC SHA-256.
4.  **Created Security Filters**:
    *   [JwtAuthenticationFilter.java](file:///c:/full%20stack%20projects/airnb%20project/services/user-service/src/main/java/com/josh/user/security/JwtAuthenticationFilter.java): Request interceptor populating Spring Security context.
    *   [SecurityConfig.java](file:///c:/full%20stack%20projects/airnb%20project/services/user-service/src/main/java/com/josh/user/security/SecurityConfig.java): Statefulness, endpoint access rules, filter chains, and BCrypt bean definitions.
5.  **Implemented User Service**: Added registration (hashes password using BCrypt encoder) and login (checks hashes, updates last login timestamp, issues JWT).
6.  **Created User Controller**: Exposes registration, login, and `/api/users/profile` retrieval (which securely checks context principal rather than parameters).

---

### Phase 4: Version Control Setup
1.  **Created `.gitignore`**: Excluded target directories and `.idea` compiler settings.
2.  **Initialized Local Repo**: Set main branch to `main`.
3.  **Configured Identity**: Registered username `Sajid Shaikh` and email `ss2727303@gmail.com`.
4.  **Linked GitHub Remote**: Connected origin to `https://github.com/sajidbaba1/Airnb-Full-Stack-Project.git`.
5.  **Merged & Pushed**: Pulled remote `README.md` using `--allow-unrelated-histories`, then pushed all local files to GitHub.

---

### Phase 5: Airline Core Microservice Development
1.  **Updated Services Aggregator POM**: Added `<module>airline-service</module>` inside [services/pom.xml](file:///c:/full%20stack%20projects/airnb%20project/services/pom.xml).
2.  **Created Configuration**: Configured port `5003`, registration in Eureka registry, and DB datasource coordinates in [application.yml](file:///c:/full%20stack%20projects/airnb%20project/services/airline-service/src/main/resources/application.yml).
3.  **Modeled Fleet Entities**: Created [Aircraft.java](file:///c:/full%20stack%20projects/airnb%20project/services/airline-service/src/main/java/com/josh/airline/entity/Aircraft.java) to manage seat layouts (left/right seating columns ABC|DEF) and capacity.
4.  **Created Repository**: Created [AircraftRepository.java](file:///c:/full%20stack%20projects/airnb%20project/services/airline-service/src/main/java/com/josh/airline/repository/AircraftRepository.java).
5.  **Created DTOs & Mappers**: Standardized payload exchange mapping in `AircraftRequestDTO` and `AircraftResponseDTO`.
6.  **Implemented Caching Services**: Added lookup and eviction rules backed by Redis caching in `AircraftServiceImpl`.
7.  **Created Controller**: Exposed REST endpoints under `/api/aircraft` in [AircraftController.java](file:///c:/full%20stack%20projects/airnb%20project/services/airline-service/src/main/java/com/josh/airline/controller/AircraftController.java).

