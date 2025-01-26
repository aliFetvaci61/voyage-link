package com.alifetvaci.travelmanagementservice.repository;

import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    Optional<Transportation> findByOriginLocation_IdAndDestinationLocation_IdAndTransportationType(long originLocationId, long destinationLocationId, TransportationType transportationType);

}
