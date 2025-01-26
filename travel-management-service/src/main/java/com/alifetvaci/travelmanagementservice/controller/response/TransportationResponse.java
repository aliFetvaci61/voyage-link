package com.alifetvaci.travelmanagementservice.controller.response;

import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TransportationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private LocationResponse originLocation;
    private LocationResponse destinationLocation;
    private TransportationType transportationType;
    private List<Integer> operatingDays;
}