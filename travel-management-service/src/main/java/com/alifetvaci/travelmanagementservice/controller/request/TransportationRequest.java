package com.alifetvaci.travelmanagementservice.controller.request;

import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TransportationRequest {

    @NotNull(message = "Origin Location ID cannot be null")
    @Positive(message = "Origin Location ID must be positive")
    private Long originLocationId;

    @NotNull(message = "Destination Location ID cannot be null")
    @Positive(message = "Destination Location ID must be positive")
    private Long destinationLocationId;

    @NotNull(message = "Transportation Type cannot be null")
    private TransportationType transportationType;

    @NotEmpty(message = "Operating Days cannot be empty")
    @Size(min = 1, message = "At least one operating day must be provided")
    private List<Integer> operatingDays;

    public boolean isValidOperatingDays() {
        return operatingDays.stream().allMatch(day -> day >= 1 && day <= 7);
    }
}
