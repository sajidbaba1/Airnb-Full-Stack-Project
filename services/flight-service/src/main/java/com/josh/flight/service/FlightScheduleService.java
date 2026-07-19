package com.josh.flight.service;

import com.josh.flight.dto.ScheduleRequestDTO;
import com.josh.flight.dto.ScheduleResponseDTO;

import java.util.List;

public interface FlightScheduleService {
    ScheduleResponseDTO createSchedule(ScheduleRequestDTO dto);
    ScheduleResponseDTO getScheduleById(Long id);
    List<ScheduleResponseDTO> getSchedulesByFlight(Long flightId);
}
