package com.josh.booking.service;

import com.josh.booking.dto.BookingRequestDTO;
import com.josh.booking.dto.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO dto);
    BookingResponseDTO getBookingByNumber(String bookingNumber);
    List<BookingResponseDTO> getBookingsByPassengerId(Long passengerId);
    BookingResponseDTO cancelBooking(String bookingNumber);
}
