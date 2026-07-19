# Learning Notes - Booking Microservice Implementation

This document provides a detailed walkthrough of the **Booking Microservice** (`booking-service`) implementation.

---

## 1. Directory Structure

The following folder structure was generated under `services/booking-service/src/main/java/com/josh/booking/`:
```text
booking-service/
├── entity/
│   ├── BookingStatus.java (Enum)
│   ├── Booking.java
│   └── PassengerDetail.java
├── repository/
│   ├── BookingRepository.java
│   └── PassengerDetailRepository.java
├── dto/
│   ├── PassengerDTO.java
│   ├── BookingRequestDTO.java
│   └── BookingResponseDTO.java
├── mapper/
│   └── BookingMapper.java
├── service/
│   ├── BookingService.java
│   └── impl/
│       └── BookingServiceImpl.java
└── controller/
    └── BookingController.java
```

---

## 2. Core Concepts Explained

### 2.1 One-to-Many Cascading persistence
When creating a reservation:
*   A `Booking` entity represents the header (confirmation number, total price, flight instance reference).
*   Each passenger in the manifest maps to a `PassengerDetail` record containing their personal credentials and assigned seat (`12F`).
*   **How JPA cascades it**: The relationship is mapped as `@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)`. When calling `bookingRepository.save(booking)`, JPA automatically flushes and persists the collection of `PassengerDetail` records in the database under a single transaction.

### 2.2 Seat Allocation validation (Dynamic Locking)
Double-booking a seat (assigning `12C` to multiple passengers on the same physical flight) is a critical integrity issue.
1.  **The Validation check**: In `BookingServiceImpl.java`, we query the database for all seat codes currently registered to active bookings for that `flightInstanceId` using:
    `passengerDetailRepository.findBookedSeatsByFlightInstanceId(flightInstanceId)`.
2.  **Occupancy comparison**: We stream these results into a fast memory lookup `Set<String>`. If any requested seat is contained in the set, we throw an `IllegalArgumentException` and rollback the transaction, ensuring data consistency.

---

## 3. Endpoints & REST Interface

*   `POST /api/bookings`: Submits a reservation request and triggers seat availability checks.
*   `GET /api/bookings/{bookingNumber}`: Retrieves booking details.
*   `GET /api/bookings/passenger/{passengerId}`: Checks transaction history for a user.
*   `PUT /api/bookings/{bookingNumber}/cancel`: Cancels reservation and releases seat columns.
