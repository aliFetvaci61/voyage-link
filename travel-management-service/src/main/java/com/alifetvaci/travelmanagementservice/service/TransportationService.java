package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.api.exception.ErrorCode;
import com.alifetvaci.travelmanagementservice.api.exception.GenericException;
import com.alifetvaci.travelmanagementservice.controller.request.TransportationRequest;
import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.repository.LocationRepository;
import com.alifetvaci.travelmanagementservice.repository.TransportationRepository;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import com.alifetvaci.travelmanagementservice.service.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alifetvaci.travelmanagementservice.service.util.Utils.mapToTransportationResponse;

@Service
@RequiredArgsConstructor
public class TransportationService {

    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public TransportationResponse createTransportation(TransportationRequest transportationRequest) {

        transportationRepository.findByOriginLocation_IdAndDestinationLocation_IdAndTransportationType(transportationRequest.getOriginLocationId(),transportationRequest.getDestinationLocationId(),transportationRequest.getTransportationType())
                .ifPresent(location -> {
                    throw GenericException.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .logMessage(ErrorCode.TRANSPORTATION_ALREADY_EXISTS.getMessage())
                            .build();
                });

        Location originLocation = locationRepository.findById(transportationRequest.getOriginLocationId())
                .orElseThrow(() -> GenericException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                        .build());

        Location destinationLocation = locationRepository.findById(transportationRequest.getDestinationLocationId())
                .orElseThrow(() -> GenericException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                        .build());

        Transportation transportation = Transportation.builder()
                .originLocation(originLocation)
                .destinationLocation(destinationLocation)
                .transportationType(transportationRequest.getTransportationType())
                .operatingDays(transportationRequest.getOperatingDays())
                .build();

        Transportation saved = transportationRepository.save(transportation);
        return mapToTransportationResponse(saved);
    }

    public List<TransportationResponse> getAllTransportations() {
        return transportationRepository.findAll().stream()
                .map(Utils::mapToTransportationResponse)
                .toList();
    }

    @Cacheable(value = "transportations", key = "#id")
    public TransportationResponse getTransportationById(Long id) {
        Transportation transportation = transportationRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .logMessage(ErrorCode.TRANSPORTATION_NOT_FOUND.getMessage())
                        .build());

        return mapToTransportationResponse(transportation);
    }

    @Transactional
    @CachePut(value = "transportations", key = "#id")
    public TransportationResponse updateTransportation(Long id, TransportationRequest transportationRequest) {
        Transportation transportation = transportationRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .logMessage(ErrorCode.TRANSPORTATION_NOT_FOUND.getMessage())
                .build());

        Location originLocation = locationRepository.findById(transportationRequest.getOriginLocationId())
                .orElseThrow(() -> GenericException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                .build());
        Location destinationLocation = locationRepository.findById(transportationRequest.getDestinationLocationId())
                .orElseThrow(() ->  GenericException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .logMessage(ErrorCode.LOCATION_NOT_FOUND.getMessage())
                .build());

        transportation.setOriginLocation(originLocation);
        transportation.setDestinationLocation(destinationLocation);
        transportation.setTransportationType(transportationRequest.getTransportationType());
        transportation.setOperatingDays(transportationRequest.getOperatingDays());

        Transportation saved = transportationRepository.save(transportation);
        return mapToTransportationResponse(saved);
    }

    @CacheEvict(value = "transportations", key = "#id")
    public void deleteTransportation(Long id) {
        if (transportationRepository.existsById(id)) {
            transportationRepository.deleteById(id);
        } else {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .logMessage(ErrorCode.TRANSPORTATION_NOT_FOUND.getMessage())
                    .build();
        }
    }
}
