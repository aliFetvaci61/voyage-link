package com.alifetvaci.travelmanagementservice.service;

import com.alifetvaci.travelmanagementservice.repository.LocationRepository;
import com.alifetvaci.travelmanagementservice.repository.TransportationRepository;
import com.alifetvaci.travelmanagementservice.repository.model.Location;
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

    private final LocationRepository locationRepository;

    private final TransportationRepository transportationRepository;

    public Map<String, Map<String, Transportation>> buildGraph() {
        Map<String, Map<String, Transportation>> graph = new HashMap<>();
        // 1. Lokasyonları al
        List<Location> locations = locationRepository.findAll();
        // 2. Her bir taşıma aracını yerleştir
        List<Transportation> transportations = transportationRepository.findAll();

        for (Transportation transportation : transportations) {

            String originCode = transportation.getOriginLocation().getLocationCode();
            String destinationCode = transportation.getDestinationLocation().getLocationCode();
            // Eğer origin lokasyonu daha önce eklenmediyse, yeni bir map oluştur
            if (!graph.containsKey(originCode)) {
                graph.put(originCode, new HashMap<>());
            }
            // Origin lokasyonu için, destination lokasyonuna giden taşıma aracını ekle
            graph.get(originCode).put(destinationCode, transportation);
        }
        return graph;
    }

    @Scheduled(cron = "0 0/3 * * * *")
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