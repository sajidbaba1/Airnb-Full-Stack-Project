package com.josh.flight.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.flight.dto.ScheduleRequestDTO;
import com.josh.flight.dto.ScheduleResponseDTO;
import com.josh.flight.entity.Flight;
import com.josh.flight.entity.FlightInstance;
import com.josh.flight.entity.FlightSchedule;
import com.josh.flight.entity.FlightStatus;
import com.josh.flight.mapper.ScheduleMapper;
import com.josh.flight.repository.FlightInstanceRepository;
import com.josh.flight.repository.FlightRepository;
import com.josh.flight.repository.FlightScheduleRepository;
import com.josh.flight.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository scheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceRepository instanceRepository;

    @Override
    @Transactional
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO dto) {
        log.info("Creating flight schedule for Flight ID: {} from {} to {}", 
                dto.getFlightId(), dto.getStartDate(), dto.getEndDate());

        // 1. Fetch parent Flight route
        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot schedule. Flight ID " + dto.getFlightId() + " not found."));

        // 2. Save Schedule entity rules
        FlightSchedule schedule = ScheduleMapper.toEntity(dto, flight);
        FlightSchedule savedSchedule = scheduleRepository.save(schedule);

        // 3. AUTOMATIC FLIGHT INSTANCE GENERATOR ENGINE
        // Loops through each date in the schedule range, checks if the day of week operates,
        // and instantiates active physical flight instances.
        generateFlightInstances(savedSchedule, dto.getBasePrice());

        return ScheduleMapper.toDTO(savedSchedule);
    }

    /**
     * Automatic instance generation algorithm.
     * Iterates day-by-day between schedule start and end dates.
     */
    private void generateFlightInstances(FlightSchedule schedule, Double basePrice) {
        LocalDate start = schedule.getStartDate();
        LocalDate end = schedule.getEndDate();
        
        // Parse the comma-separated operating days string into a Set of DayOfWeek names
        // E.g., "MONDAY,WEDNESDAY" -> Set.of("MONDAY", "WEDNESDAY")
        Set<String> activeDays = Arrays.stream(schedule.getOperatingDays().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        log.info("Generator running for days: {}", activeDays);

        LocalDate current = start;
        int generatedCount = 0;

        // Traverse date range sequentially
        while (!current.isAfter(end)) {
            String currentDayName = current.getDayOfWeek().name(); // E.g., "MONDAY"

            // If the day operates, instantiate flight
            if (activeDays.contains(currentDayName)) {
                // Prevent duplicate instance generation on the same date for this route
                if (!instanceRepository.existsByFlightIdAndDate(schedule.getFlight().getId(), current)) {
                    FlightInstance instance = FlightInstance.builder()
                            .flight(schedule.getFlight())
                            .schedule(schedule)
                            .aircraftId(schedule.getAircraftId())
                            .date(current)
                            .status(FlightStatus.SCHEDULED)
                            .basePrice(basePrice)
                            .build();

                    instanceRepository.save(instance);
                    generatedCount++;
                }
            }
            current = current.plusDays(1); // Increment calendar day
        }

        log.info("Automatic generator completed. Created {} flight instances.", generatedCount);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleById(Long id) {
        FlightSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with ID: " + id));
        return ScheduleMapper.toDTO(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> getSchedulesByFlight(Long flightId) {
        return scheduleRepository.findByFlightId(flightId).stream()
                .map(ScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
