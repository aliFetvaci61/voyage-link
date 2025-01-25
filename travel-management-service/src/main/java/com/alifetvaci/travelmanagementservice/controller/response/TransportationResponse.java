package com.alifetvaci.travelmanagementservice.controller.response;

import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransportationResponse {
    private Long id;
    private LocationResponse originLocation;
    private LocationResponse destinationLocation;
    private TransportationType transportationType;
    private List<Integer> operatingDays;
}