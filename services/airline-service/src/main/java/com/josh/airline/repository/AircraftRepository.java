package com.josh.airline.repository;

import com.josh.airline.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AircraftRepository manages data access queries for Aircraft entities.
 * 
 * JpaRepository provides default SQL query actions for saving, finding, and deleting 
 * records under the AIRCRAFT table.
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
