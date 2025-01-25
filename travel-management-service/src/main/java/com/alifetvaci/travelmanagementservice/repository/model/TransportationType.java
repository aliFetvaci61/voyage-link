package com.alifetvaci.travelmanagementservice.repository.model;

public enum TransportationType {
    FLIGHT("Flight"),
    BUS("Bus"),
    SUBWAY("Subway"),
    UBER("Uber");

    private final String displayName;

    TransportationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}