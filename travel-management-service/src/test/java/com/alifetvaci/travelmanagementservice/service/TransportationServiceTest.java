package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.api.exception.ErrorCode;
import com.alifetvaci.travelmanagementservice.api.exception.GenericException;
import com.alifetvaci.travelmanagementservice.controller.request.TransportationRequest;
import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.repository.LocationRepository;
import com.alifetvaci.travelmanagementservice.repository.TransportationRepository;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransportationServiceTest {

    @Mock
    private TransportationRepository transportationRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private TransportationService transportationService;

    private Location originLocation;
    private Location destinationLocation;
    private TransportationRequest transportationRequest;
    private Transportation transportation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        originLocation = Location.builder()
                .id(1L)
                .name("Origin Location")
                .country("CountryA")
                .city("CityA")
                .locationCode("LOC123")
                .build();

        destinationLocation = Location.builder()
                .id(2L)
                .name("Destination Location")
                .country("CountryB")
                .city("CityB")
                .locationCode("LOC124")
                .build();

        transportationRequest = new TransportationRequest();
        transportationRequest.setOriginLocationId(1L);
        transportationRequest.setDestinationLocationId(2L);
        transportationRequest.setTransportationType(TransportationType.FLIGHT);
        transportationRequest.setOperatingDays(List.of(1, 3, 5));

        transportation = Transportation.builder()
                .originLocation(originLocation)
                .destinationLocation(destinationLocation)
                .transportationType(TransportationType.FLIGHT)
                .operatingDays(List.of(1, 3, 5))
                .build();
    }

    @Test
    void testCreateTransportation() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(originLocation));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(destinationLocation));
        when(transportationRepository.save(any(Transportation.class))).thenReturn(transportation);

        TransportationResponse response = transportationService.createTransportation(transportationRequest);

        assertNotNull(response);
        assertEquals(TransportationType.FLIGHT, response.getTransportationType());
        verify(locationRepository, times(2)).findById(anyLong());
        verify(transportationRepository, times(1)).save(any(Transportation.class));
    }

    @Test
    void testCreateTransportation_LocationNotFound() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> {
            transportationService.createTransportation(transportationRequest);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ErrorCode.LOCATION_NOT_FOUND.getMessage(), exception.getLogMessage());
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllTransportations() {
        when(transportationRepository.findAll()).thenReturn(List.of(transportation));

        List<TransportationResponse> responses = transportationService.getAllTransportations();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(transportationRepository, times(1)).findAll();
    }

    @Test
    void testGetTransportationById() {
        when(transportationRepository.findById(1L)).thenReturn(Optional.of(transportation));

        TransportationResponse response = transportationService.getTransportationById(1L);

        assertNotNull(response);
        assertEquals(TransportationType.FLIGHT, response.getTransportationType());
        verify(transportationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransportationById_NotFound() {
        when(transportationRepository.findById(1L)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> {
            transportationService.getTransportationById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ErrorCode.TRANSPORTATION_NOT_FOUND.getMessage(), exception.getLogMessage());
        verify(transportationRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTransportation() {
        when(transportationRepository.findById(1L)).thenReturn(Optional.of(transportation));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(originLocation));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(destinationLocation));
        when(transportationRepository.save(any(Transportation.class))).thenReturn(transportation);

        TransportationResponse response = transportationService.updateTransportation(1L, transportationRequest);

        assertNotNull(response);
        assertEquals(TransportationType.FLIGHT, response.getTransportationType());
        verify(transportationRepository, times(1)).findById(1L);
        verify(locationRepository, times(2)).findById(anyLong());
        verify(transportationRepository, times(1)).save(any(Transportation.class));
    }

    @Test
    void testDeleteTransportation() {
        when(transportationRepository.existsById(1L)).thenReturn(true);

        transportationService.deleteTransportation(1L);

        verify(transportationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTransportation_NotFound() {
        when(transportationRepository.existsById(1L)).thenReturn(false);

        GenericException exception = assertThrows(GenericException.class, () -> {
            transportationService.deleteTransportation(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ErrorCode.TRANSPORTATION_NOT_FOUND.getMessage(), exception.getLogMessage());
        verify(transportationRepository, times(1)).existsById(1L);
    }
}
