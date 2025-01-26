package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.api.exception.ErrorCode;
import com.alifetvaci.travelmanagementservice.api.exception.GenericException;
import com.alifetvaci.travelmanagementservice.controller.request.LocationRequest;
import com.alifetvaci.travelmanagementservice.controller.response.LocationResponse;
import com.alifetvaci.travelmanagementservice.repository.LocationRepository;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
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

class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location location;
    private LocationRequest locationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        location = Location.builder()
                .id(1L)
                .name("Istanbul")
                .country("Turkey")
                .city("Istanbul")
                .locationCode("IST")
                .build();

        locationRequest = LocationRequest.builder()
                .name("Ankara")
                .country("Turkey")
                .city("Ankara")
                .locationCode("ANK")
                .build();
    }

    @Test
    void testCreateLocation() {
        // Arrange
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        // Act
        LocationResponse response = locationService.createLocation(locationRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Istanbul", response.getName());
        assertEquals("Turkey", response.getCountry());
        assertEquals("Ankara", locationRequest.getName());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void testGetAllLocations() {
        // Arrange
        when(locationRepository.findAll()).thenReturn(List.of(location));

        // Act
        List<LocationResponse> responses = locationService.getAllLocations();

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Istanbul", responses.get(0).getName());
    }

    @Test
    void testGetLocationById_Found() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        // Act
        LocationResponse response = locationService.getLocationById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("Istanbul", response.getName());
    }

    @Test
    void testGetLocationById_NotFound() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            locationService.getLocationById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ErrorCode.LOCATION_NOT_FOUND.getMessage(), exception.getLogMessage());
    }

    @Test
    void testUpdateLocation() {
        // Arrange
        Location updatedLocation = Location.builder()
                .id(1L)
                .name("Updated Location")
                .country("Turkey")
                .city("Istanbul")
                .locationCode("IST")
                .build();

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(locationRepository.save(any(Location.class))).thenReturn(updatedLocation);

        // Act
        LocationResponse response = locationService.updateLocation(1L, locationRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Location", response.getName());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void testDeleteLocation_Found() {
        // Arrange
        when(locationRepository.existsById(1L)).thenReturn(true);

        // Act
        locationService.deleteLocation(1L);

        // Assert
        verify(locationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLocation_NotFound() {
        // Arrange
        when(locationRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            locationService.deleteLocation(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ErrorCode.LOCATION_NOT_FOUND.getMessage(), exception.getLogMessage());
    }
}