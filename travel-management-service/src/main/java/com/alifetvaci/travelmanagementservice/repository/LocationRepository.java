package com.alifetvaci.travelmanagementservice.repository;


import com.alifetvaci.travelmanagementservice.repository.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
