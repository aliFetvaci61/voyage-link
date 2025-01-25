package com.alifetvaci.travelmanagementservice.controller.request;

import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import lombok.Data;

import java.util.List;

@Data
public class TransportationRequest {
    private Long originLocationId;
    private Long destinationLocationId;
    private TransportationType transportationType; // FLIGHT, BUS, etc.
    private List<Integer> operatingDays;
}
