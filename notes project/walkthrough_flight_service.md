# Learning Notes - Flight Operation Microservice Implementation

This document provides a detailed walkthrough of the **Flight Operation Microservice** (`flight-service`) implementation.

---

## 1. Directory Structure

The following folder structure was generated under `services/flight-service/src/main/java/com/josh/flight/`:
```text
flight-service/
├── entity/
│   ├── FlightStatus.java (Enum)
│   ├── Flight.java
│   ├── FlightSchedule.java
│   └── FlightInstance.java
├── repository/
│   ├── FlightRepository.java
│   ├── FlightScheduleRepository.java
│   └── FlightInstanceRepository.java
├── dto/
│   ├── FlightRequestDTO.java
│   ├── FlightResponseDTO.java
│   ├── ScheduleRequestDTO.java
│   ├── ScheduleResponseDTO.java
│   └── InstanceResponseDTO.java
├── mapper/
│   ├── FlightMapper.java
│   ├── ScheduleMapper.java
│   └── InstanceMapper.java
├── service/
│   ├── FlightService.java
│   ├── FlightScheduleService.java
│   ├── FlightInstanceService.java
│   └── impl/
│       ├── FlightServiceImpl.java
│       ├── FlightScheduleServiceImpl.java
│       └── FlightInstanceServiceImpl.java
└── controller/
    ├── FlightController.java
    ├── FlightScheduleController.java
    └── FlightInstanceController.java
```

---

## 2. Core Concepts Explained

### 2.1 Microservices Distributed database Design
In monolithic databases, tables are linked by database-level foreign keys. In a clean microservices architecture:
*   `Flight` stores reference IDs for entities belonging to other services (`departureAirportId`, `arrivalAirportId` owned by Location Service, and `airlineId` owned by Airline Service).
*   We maintain these links **logically** inside code. We do **NOT** define SQL foreign key constraints. This keeps the service schema completely autonomous.

### 2.2 Automatic Flight Instance Generator Engine
A Flight Schedule represents a plan (e.g. Flight 101 runs MONDAY and FRIDAY, from April 1 to April 30). Customers do not book a schedule; they book a physical seat on a flight operating on a specific date.

The **Generator Engine** in `FlightScheduleServiceImpl.java` automates this conversion:
1.  When a schedule is created, it parses `operatingDays` (e.g. `"MONDAY,WEDNESDAY"`).
2.  It starts on `startDate` and runs a `while` loop, checking each date up to `endDate` (`current.plusDays(1)`).
3.  For each day, it retrieves the weekday string (`current.getDayOfWeek().name()`) and compares it against active days.
4.  If it matches, it checks `instanceRepository.existsByFlightIdAndDate` to prevent double generation, and instantiates a `FlightInstance` row.

---

## 3. Endpoints & REST Interface

*   `POST /api/flights`: Declares a route link.
*   `POST /api/schedules`: Defines scheduling rules and fires the auto-generator.
*   `GET /api/instances/flight/{flightId}/search`: Searches physical flights by specific calendar dates.
*   `PUT /api/instances/{id}/status`: Updates operations status (e.g., delaying or cancelling flights).
