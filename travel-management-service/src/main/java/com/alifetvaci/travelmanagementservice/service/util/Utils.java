package com.alifetvaci.travelmanagementservice.service.util;

import com.alifetvaci.travelmanagementservice.controller.response.LocationResponse;
import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import lombok.experimental.UtilityClass;

    @UtilityClass
    public class Utils {

        public static LocationResponse mapToLocationResponse(Location location) {
            return LocationResponse.builder()
                    .id(location.getId())
                    .name(location.getName())
                    .country(location.getCountry())
                    .city(location.getCity())
                    .locationCode(location.getLocationCode())
                    .build();
        }

        public static TransportationResponse mapToTransportationResponse(Transportation transportation) {
            return TransportationResponse.builder()
                    .id(transportation.getId())
                    .originLocation(mapToLocationResponse(transportation.getOriginLocation()))
                    .destinationLocation(mapToLocationResponse(transportation.getDestinationLocation()))
                    .transportationType(transportation.getTransportationType())
                    .operatingDays(transportation.getOperatingDays())
                    .build();
        }
    }
