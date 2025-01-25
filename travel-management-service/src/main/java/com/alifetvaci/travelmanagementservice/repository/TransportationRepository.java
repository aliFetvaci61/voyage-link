package com.alifetvaci.travelmanagementservice.repository;

import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

}
