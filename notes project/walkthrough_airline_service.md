# Learning Notes - Airline Core Microservice Implementation

This document provides a detailed walkthrough of the **Airline Core Microservice** (`airline-service`) implementation.

---

## 1. Directory Structure

The following folder structure was generated under `services/airline-service/src/main/java/com/josh/airline/`:
```text
airline-service/
├── entity/
│   └── Aircraft.java
├── repository/
│   └── AircraftRepository.java
├── dto/
│   ├── AircraftRequestDTO.java
│   └── AircraftResponseDTO.java
├── mapper/
│   └── AircraftMapper.java
├── service/
│   ├── AircraftService.java
│   └── impl/
│       └── AircraftServiceImpl.java
└── controller/
    └── AircraftController.java
```

---

## 2. Core Concepts Explained

### 2.1 Seating Column Layout Configuration
To support airline admins setting up seat maps dynamically (shown in the live demo), the aircraft entity stores:
*   `leftSeatColumns` (e.g. `ABC`): Seating columns on the left of the aisle.
*   `rightSeatColumns` (e.g. `DEF`): Seating columns on the right of the aisle.
This makes seat map rendering highly modular. A Boeing 737 might have `ABC` and `DEF` (6 columns per row), while a wider Airbus A350 might have `AC` (left), `DEFG` (middle), and `HK` (right) configurations.

### 2.2 Redis Caching for Fleet profiles
Aircraft profiles are relatively static reference data:
*   **The caching approach**: We annotate lookups with `@Cacheable(value = "aircrafts", key = "#id")`. The first query fetches details from MySQL/Oracle, stores it in Redis, and subsequent lookups fetch the result in under 1-2ms directly from RAM.
*   **Eviction policy**: To prevent stale caches when changes occur, we annotate modifications with `@CacheEvict(value = "aircrafts", allEntries = true)`. This flushes the Redis namespace, forcing the service to read updated profiles from the database on next query.

---

## 3. Endpoints & REST Interface

*   `POST /api/aircraft`: Registers a new fleet configuration with capacity constraints.
*   `GET /api/aircraft`: Fetches lists of all registered fleets.
*   `GET /api/aircraft/{id}`: Fetches aircraft layouts by ID.
