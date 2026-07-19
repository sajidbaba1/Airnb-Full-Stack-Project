package com.josh.booking.controller;

import com.josh.booking.dto.BookingRequestDTO;
import com.josh.booking.dto.BookingResponseDTO;
import com.josh.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Endpoint to reserve tickets and automatically validate seat occupancy.
     * @Valid triggers automatic validation on fields (manifestNotEmpty, amountNotNull, etc.).
     */
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO dto) {
        BookingResponseDTO response = bookingService.createBooking(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve booking details by booking reference number.
     */
    @GetMapping("/{bookingNumber}")
    public ResponseEntity<BookingResponseDTO> getBookingByNumber(@PathVariable String bookingNumber) {
        return ResponseEntity.ok(bookingService.getBookingByNumber(bookingNumber));
    }

    /**
     * Endpoint to query bookings made by a specific user.
     */
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(bookingService.getBookingsByPassengerId(passengerId));
    }

    /**
     * Endpoint to cancel a reservation.
     */
    @PutMapping("/{bookingNumber}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable String bookingNumber) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingNumber));
    }
}
