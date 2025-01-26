package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.repository.TransportationRepository;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransportGraphService {

    public static Map<String, Map<String, Transportation>> graph;

    private final TransportationRepository transportationRepository;

    public Map<String, Map<String, Transportation>> buildGraph() {
        Map<String, Map<String, Transportation>> graph = new HashMap<>();
        // 1. Place each transportation vehicle
        List<Transportation> transportations = transportationRepository.findAll();

        for (Transportation transportation : transportations) {

            String originCode = transportation.getOriginLocation().getLocationCode();
            String destinationCode = transportation.getDestinationLocation().getLocationCode();
            // If the origin location has not been added before, create a new map
            if (!graph.containsKey(originCode)) {
                graph.put(originCode, new HashMap<>());
            }
            // For the origin location, add the transportation vehicle to the destination location
            graph.get(originCode).put(destinationCode, transportation);
        }
        return graph;
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void scheduleBuildGraph() {
        graph = buildGraph();
    }

    @PostConstruct
    public void onStartup() {
        scheduleBuildGraph();
    }

    public Map<String, Map<String, Transportation>> getTransportationGraph() {
        return graph;
    }
}