package com.josh.booking.mapper;

import com.josh.booking.dto.BookingRequestDTO;
import com.josh.booking.dto.BookingResponseDTO;
import com.josh.booking.dto.PassengerDTO;
import com.josh.booking.entity.Booking;
import com.josh.booking.entity.PassengerDetail;

import java.util.stream.Collectors;

public class BookingMapper {

    public static Booking toEntity(BookingRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Booking booking = Booking.builder()
                .flightInstanceId(dto.getFlightInstanceId())
                .passengerId(dto.getPassengerId())
                .totalAmount(dto.getTotalAmount())
                .build();

        if (dto.getPassengers() != null) {
            dto.getPassengers().forEach(p -> {
                PassengerDetail detail = PassengerDetail.builder()
                        .name(p.getName())
                        .email(p.getEmail())
                        .age(p.getAge())
                        .gender(p.getGender())
                        .assignedSeat(p.getAssignedSeat().toUpperCase().trim())
                        .build();
                booking.addPassenger(detail);
            });
        }

        return booking;
    }

    public static BookingResponseDTO toDTO(Booking entity) {
        if (entity == null) {
            return null;
        }

        List<PassengerDTO> passengers = null;
        if (entity.getPassengers() != null) {
            passengers = entity.getPassengers().stream()
                    .map(p -> PassengerDTO.builder()
                            .name(p.getName())
                            .email(p.getEmail())
                            .age(p.getAge())
                            .gender(p.getGender())
                            .assignedSeat(p.getAssignedSeat())
                            .build())
                    .collect(Collectors.toList());
        }

        return BookingResponseDTO.builder()
                .id(entity.getId())
                .bookingNumber(entity.getBookingNumber())
                .flightInstanceId(entity.getFlightInstanceId())
                .passengerId(entity.getPassengerId())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus())
                .bookingDate(entity.getBookingDate())
                .passengers(passengers)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
