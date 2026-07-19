package com.josh.booking.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.booking.dto.BookingRequestDTO;
import com.josh.booking.dto.BookingResponseDTO;
import com.josh.booking.dto.PassengerDTO;
import com.josh.booking.entity.Booking;
import com.josh.booking.entity.BookingStatus;
import com.josh.booking.mapper.BookingMapper;
import com.josh.booking.repository.BookingRepository;
import com.josh.booking.repository.PassengerDetailRepository;
import com.josh.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerDetailRepository passengerDetailRepository;

    /**
     * Executes ticket reservation with seat validations.
     * 
     * LEARNING POINT:
     * @Transactional ensures that seat check and database persistence are treated as a single unit 
     * of work. If seat validation fails or a database constraint is violated, the transaction 
     * is rolled back completely, guaranteeing no corrupt/partial booking states are committed.
     */
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        log.info("Processing booking reservation for Flight Instance ID: {}", dto.getFlightInstanceId());

        // 1. Fetch currently occupied seats for this flight instance (from active confirmed bookings)
        List<String> takenSeats = passengerDetailRepository.findBookedSeatsByFlightInstanceId(dto.getFlightInstanceId());
        Set<String> occupiedSeatSet = takenSeats.stream()
                .map(String::toUpperCase)
                .map(String::trim)
                .collect(Collectors.toSet());

        log.info("Checking seat availability. Currently occupied seats: {}", occupiedSeatSet);

        // 2. Validate seat selection for each passenger in the manifest
        for (PassengerDTO passenger : dto.getPassengers()) {
            String seat = passenger.getAssignedSeat().toUpperCase().trim();
            if (occupiedSeatSet.contains(seat)) {
                log.error("Validation failed. Seat {} is already occupied.", seat);
                throw new IllegalArgumentException("Seat " + seat + " is already occupied on this flight.");
            }
        }

        // 3. Map DTO inputs to Booking Entity
        Booking booking = BookingMapper.toEntity(dto);

        // 4. Generate a unique booking confirmation number (alphanumeric, prefixed BK-)
        String bookingNumber = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        booking.setBookingNumber(bookingNumber);
        booking.setStatus(BookingStatus.CONFIRMED); // Automatically confirmed on validation success

        // 5. Persist to DB (One-to-Many cascade will automatically save PassengerDetail children)
        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking reserved successfully. Number: {}, ID: {}", bookingNumber, savedBooking.getId());

        return BookingMapper.toDTO(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDTO getBookingByNumber(String bookingNumber) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber.toUpperCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with reference number: " + bookingNumber));
        return BookingMapper.toDTO(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getBookingsByPassengerId(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId).stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(String bookingNumber) {
        log.info("Cancelling booking number: {}", bookingNumber);
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber.toUpperCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with reference number: " + bookingNumber));
        
        booking.setStatus(BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);
        
        log.info("Successfully cancelled booking number: {}", bookingNumber);
        return BookingMapper.toDTO(updated);
    }
}
