package com.josh.flight.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.flight.dto.InstanceResponseDTO;
import com.josh.flight.entity.FlightInstance;
import com.josh.flight.entity.FlightStatus;
import com.josh.flight.mapper.InstanceMapper;
import com.josh.flight.repository.FlightInstanceRepository;
import com.josh.flight.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository instanceRepository;

    @Override
    @Transactional(readOnly = true)
    public InstanceResponseDTO getInstanceById(Long id) {
        FlightInstance instance = instanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight instance not found with ID: " + id));
        return InstanceMapper.toDTO(instance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstanceResponseDTO> getInstancesByFlight(Long flightId) {
        return instanceRepository.findByFlightId(flightId).stream()
                .map(InstanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstanceResponseDTO> getInstancesByFlightAndDate(Long flightId, LocalDate date) {
        return instanceRepository.findByFlightIdAndDate(flightId, date).stream()
                .map(InstanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InstanceResponseDTO updateInstanceStatus(Long id, FlightStatus status) {
        log.info("Updating Flight Instance ID: {} status to {}", id, status);
        FlightInstance instance = instanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight instance not found with ID: " + id));
        
        instance.setStatus(status);
        FlightInstance updated = instanceRepository.save(instance);
        
        return InstanceMapper.toDTO(updated);
    }
}
