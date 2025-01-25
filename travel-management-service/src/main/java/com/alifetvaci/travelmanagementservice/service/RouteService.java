package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import com.alifetvaci.travelmanagementservice.repository.model.TransportationType;
import com.alifetvaci.travelmanagementservice.service.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final TransportGraphService transportGraphService;

    public List<List<TransportationResponse>> findRoutes(String from, String to, LocalDate date) {
        List<List<Transportation>> routes = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        findRoutesDFS(from, to, visited, new ArrayList<>(), routes, false, date);

        return convertRoutes(routes);
    }

    private void findRoutesDFS(String current, String destination, Set<String> visited,
                               List<Transportation> currentRoute, List<List<Transportation>> routes,
                               boolean flightFound, LocalDate date) {

        // Stop if the route has more than 3 modes of transportation
        if (currentRoute.size() > 3) return;

        // Check for consecutive flights
        long flightCount = currentRoute.stream()
                .filter(t -> t.getTransportationType().equals(TransportationType.FLIGHT))
                .count();
        if (flightCount > 1) return;

        // Check the limit of transfers before the flight:
        long transfersBeforeFlight = currentRoute.stream()
                .takeWhile(t -> !t.getTransportationType().equals(TransportationType.FLIGHT))
                .count();
        if (flightCount == 0 && transfersBeforeFlight > 1) return;

        // Check the limit of transfers after the flight:
        long transfersAfterFlight = currentRoute.stream()
                .dropWhile(t -> !t.getTransportationType().equals(TransportationType.FLIGHT))
                .skip(1) // Skip the flight itself
                .count();
        if (flightFound && transfersAfterFlight > 1) return;

        // We've reached the destination and there is a flight
        if (current.equals(destination) && flightFound) {
            routes.add(new ArrayList<>(currentRoute));
            return;
        }
        visited.add(current);
        // Check all modes of transportation from the current location
        Map<String, Map<String, Transportation>> graph = transportGraphService.getTransportationGraph();
        Map<String, Transportation> adjacentLocations = graph.get(current);

        // If the current location has no neighbors, return
        if (adjacentLocations == null) {
            visited.remove(current);
            return;
        }

        for (Map.Entry<String, Transportation> entry : adjacentLocations.entrySet()) {
            String nextLocation = entry.getKey();
            Transportation transportation = entry.getValue();
            // If the transportation operates on the specified date, it's valid
            if (!transportation.getOperatingDays().contains(date.getDayOfWeek().getValue())) {
                continue;
            }
            // Skip if we have already visited this location
            if (visited.contains(nextLocation)) continue;
            // If it's a flight, mark that a flight has been found
            boolean newFlightFound = flightFound || transportation.getTransportationType().equals(TransportationType.FLIGHT);
            // Add the transportation to the route and call DFS
            currentRoute.add(transportation);
            findRoutesDFS(nextLocation, destination, visited, currentRoute, routes, newFlightFound, date);
            currentRoute.remove(transportation);
        }
        visited.remove(current);
    }


    public List<List<TransportationResponse>> convertRoutes(List<List<Transportation>> routes) {
        return routes.stream()
                .map(innerList -> innerList.stream()
                        .map(Utils::mapToTransportationResponse)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

}
