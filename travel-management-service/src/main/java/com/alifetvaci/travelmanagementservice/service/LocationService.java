package com.alifetvaci.travelmanagementservice.service;


import com.alifetvaci.travelmanagementservice.api.exception.ErrorCode;
import com.alifetvaci.travelmanagementservice.api.exception.GenericException;
import com.alifetvaci.travelmanagementservice.controller.request.LocationRequest;
import com.alifetvaci.travelmanagementservice.controller.response.LocationResponse;
import com.alifetvaci.travelmanagementservice.repository.LocationRepository;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
import com.alifetvaci.travelmanagementservice.service.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationResponse createLocation(LocationRequest locationRequest) {
        Location location = Location.builder()
                .name(locationRequest.getName())
                .country(locationRequest.getCountry())
                .city(locationRequest.getCity())
                .locationCode(locationRequest.getLocationCode())
                .build();

        Location saved = locationRepository.save(location);
        return Utils.mapToLocationResponse(saved);

    }

    public List<LocationResponse> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(Utils::mapToLocationResponse)
                .toList();
    }

    @Cacheable(value = "locations", key = "#id")
    public LocationResponse getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                        .build());

        return Utils.mapToLocationResponse(location);
    }

    @Transactional
    @CachePut(value = "locations", key = "#id")
    public LocationResponse updateLocation(Long id, LocationRequest locationRequest) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                        .build());

        existingLocation.setName(locationRequest.getName());
        existingLocation.setCountry(locationRequest.getCountry());
        existingLocation.setCity(locationRequest.getCity());
        existingLocation.setLocationCode(locationRequest.getLocationCode());

        Location saved = locationRepository.save(existingLocation);
        return Utils.mapToLocationResponse(saved);
    }


    @CacheEvict(value = "locations", key = "#id")
    public void deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                    .build();
        }
    }
}
