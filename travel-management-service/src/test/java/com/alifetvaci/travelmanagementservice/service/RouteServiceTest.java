package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import com.alifetvaci.travelmanagementservice.service.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RouteServiceTest {

    @Mock
    private TransportGraphService transportGraphService;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findRoutes_shouldReturnValidRoutes() {
        // Arrange
        String from = "A";
        String to = "C";
        LocalDate date = LocalDate.of(2025, 1, 26);

        Location locationA = Location.builder()
                .name("Location A")
                .country("Country A")
                .city("City A")
                .locationCode("A")
                .build();

        Location locationB = Location.builder()
                .name("Location B")
                .country("Country B")
                .city("City B")
                .locationCode("B")
                .build();

        Location locationC = Location.builder()
                .name("Location C")
                .country("Country C")
                .city("City C")
                .locationCode("C")
                .build();

        Transportation transportation1 = Transportation.builder()
                .originLocation(locationA)
                .destinationLocation(locationB)
                .transportationType(TransportationType.BUS)
                .operatingDays(Arrays.asList(1, 2, 3, 4, 5, 6, 7))
                .build();

        Transportation transportation2 = Transportation.builder()
                .originLocation(locationB)
                .destinationLocation(locationC)
                .transportationType(TransportationType.FLIGHT)
                .operatingDays(Arrays.asList(1, 3, 5, 7))
                .build();

        Map<String, Map<String, Transportation>> graph = new HashMap<>();
        Map<String, Transportation> fromMap = new HashMap<>();
        fromMap.put("B", transportation1);
        graph.put("A", fromMap);

        Map<String, Transportation> toMap = new HashMap<>();
        toMap.put("C", transportation2);
        graph.put("B", toMap);

        when(transportGraphService.getTransportationGraph()).thenReturn(graph);

        // Act
        List<List<TransportationResponse>> routes = routeService.findRoutes(from, to, date);

        // Assert
        assertEquals(1, routes.size());
        verify(transportGraphService, times(1)).getTransportationGraph();
    }


    @Test
    void findRoutes_shouldReturnEmptyWhenNoValidRoutes() {
        // Arrange
        String from = "A";
        String to = "C";
        LocalDate date = LocalDate.of(2025, 1, 26);

        Location locationA = Location.builder()
                .name("Location A")
                .country("Country A")
                .city("City A")
                .locationCode("A")
                .build();

        Location locationB = Location.builder()
                .name("Location B")
                .country("Country B")
                .city("City B")
                .locationCode("B")
                .build();

        Location locationC = Location.builder()
                .name("Location C")
                .country("Country C")
                .city("City C")
                .locationCode("C")
                .build();

        Transportation transportation1 = Transportation.builder()
                .originLocation(locationA)
                .destinationLocation(locationB)
                .transportationType(TransportationType.BUS)
                .operatingDays(Arrays.asList(1, 2, 3, 4, 5, 6, 7))
                .build();

        Transportation transportation2 = Transportation.builder()
                .originLocation(locationB)
                .destinationLocation(locationC)
                .transportationType(TransportationType.BUS)
                .operatingDays(Arrays.asList(1, 3, 5, 7))
                .build();

        Map<String, Map<String, Transportation>> graph = new HashMap<>();
        Map<String, Transportation> fromMap = new HashMap<>();
        fromMap.put("B", transportation1);
        graph.put("A", fromMap);

        Map<String, Transportation> toMap = new HashMap<>();
        toMap.put("C", transportation2);
        graph.put("B", toMap);

        when(transportGraphService.getTransportationGraph()).thenReturn(Collections.emptyMap());

        // Act
        List<List<TransportationResponse>> routes = routeService.findRoutes(from, to, date);

        // Assert
        assertEquals(0, routes.size());
        verify(transportGraphService, times(1)).getTransportationGraph();
    }

    @Test
    void convertRoutes_shouldConvertCorrectly() {
        // Arrange
        Transportation transportation1 = Transportation.builder()
                .transportationType(TransportationType.BUS)
                .operatingDays(Arrays.asList(1, 2, 3))
                .build();

        Transportation transportation2 = Transportation.builder()
                .transportationType(TransportationType.FLIGHT)
                .operatingDays(Arrays.asList(4, 5))
                .build();

        List<List<Transportation>> routes = new ArrayList<>();
        routes.add(Arrays.asList(transportation1, transportation2));

        TransportationResponse response1 = TransportationResponse.builder()
                .transportationType(TransportationType.BUS)
                .build();

        TransportationResponse response2 = TransportationResponse.builder()
                .transportationType(TransportationType.FLIGHT)
                .build();

        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
            mockedUtils.when(() -> Utils.mapToTransportationResponse(transportation1)).thenReturn(response1);
            mockedUtils.when(() -> Utils.mapToTransportationResponse(transportation2)).thenReturn(response2);

            // Act
            List<List<TransportationResponse>> result = routeService.convertRoutes(routes);

            // Assert
            assertEquals(1, result.size());
            assertEquals(2, result.get(0).size());
            assertEquals(TransportationType.BUS, result.get(0).get(0).getTransportationType());
            assertEquals(TransportationType.FLIGHT, result.get(0).get(1).getTransportationType());
        }
    }
}
