package com.alifetvaci.travelmanagementservice.repository.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum TransportationType implements Serializable {
    FLIGHT("Flight"),
    BUS("Bus"),
    SUBWAY("Subway"),
    UBER("Uber");

    private final String displayName;

    TransportationType(String displayName) {
        this.displayName = displayName;
    }

}