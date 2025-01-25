package com.alifetvaci.travelmanagementservice.api.exception;

public enum ErrorCode {
    TRANSPORTATION_NOT_FOUND("Transportation not found"),
    LOCATION_NOT_FOUND("Location not found");


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
