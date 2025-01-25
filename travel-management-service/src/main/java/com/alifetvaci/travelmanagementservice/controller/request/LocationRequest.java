package com.alifetvaci.travelmanagementservice.controller.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LocationRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String locationCode;

}
