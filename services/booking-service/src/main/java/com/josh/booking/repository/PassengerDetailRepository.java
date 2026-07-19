package com.josh.booking.repository;

import com.josh.booking.entity.PassengerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDetailRepository extends JpaRepository<PassengerDetail, Long> {
    
    /**
     * Retrieves all assigned seat numbers for a specific flight instance where 
     * the booking status is active (not cancelled).
     * 
     * LEARNING POINT:
     * This JPQL query joins PassengerDetail with parent Booking table and extracts only 
     * the assignedSeat column. Doing this at DB level is faster and consumes 
     * far less memory than pulling all parent objects into Java heap.
     */
    @Query("SELECT p.assignedSeat FROM PassengerDetail p " +
           "WHERE p.booking.flightInstanceId = :flightInstanceId " +
           "AND p.booking.status <> com.josh.booking.entity.BookingStatus.CANCELLED")
    List<String> findBookedSeatsByFlightInstanceId(@Param("flightInstanceId") Long flightInstanceId);
}
