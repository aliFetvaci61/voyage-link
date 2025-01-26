package com.alifetvaci.travelmanagementservice.controller.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LocationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String country;
    private String city;
    private String locationCode;
}
