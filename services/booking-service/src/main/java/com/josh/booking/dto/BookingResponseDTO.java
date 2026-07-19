package com.josh.booking.dto;

import com.josh.booking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private String bookingNumber;
    private Long flightInstanceId;
    private Long passengerId;
    private Double totalAmount;
    private BookingStatus status;
    private LocalDateTime bookingDate;
    private List<PassengerDTO> passengers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
