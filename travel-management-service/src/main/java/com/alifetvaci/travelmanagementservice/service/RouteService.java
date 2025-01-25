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

        // Eğer rota 3 taşıma aracından fazla olursa durduruyoruz
        if (currentRoute.size() > 3) return;

        // Arka arkaya birden fazla uçuş kontrolü
        long flightCount = currentRoute.stream()
                .filter(t -> t.getTransportationType().equals(TransportationType.FLIGHT))
                .count();
        if (flightCount > 1) return;

        //Uçuş öncesi transfer sayısı sınırı kontrolü:
        long transfersBeforeFlight = currentRoute.stream()
                .takeWhile(t -> !t.getTransportationType().equals(TransportationType.FLIGHT))
                .count();
        if (flightCount == 0 && transfersBeforeFlight > 1) return;

        //Uçuş sonrası transfer sayısı sınırı kontrolü:
        long transfersAfterFlight = currentRoute.stream()
                .dropWhile(t -> !t.getTransportationType().equals(TransportationType.FLIGHT))
                .skip(1) // Uçuşu atla
                .count();
        if (flightFound && transfersAfterFlight > 1) return;

        // Hedef lokasyona ulaştık ve bir uçuş var
        if (current.equals(destination) && flightFound) {
            routes.add(new ArrayList<>(currentRoute));
            return;
        }
        visited.add(current);
        // Mevcut lokasyondan gidebileceğimiz tüm taşıma araçlarını kontrol et
        Map<String, Map<String, Transportation>> graph = transportGraphService.getTransportationGraph();
        Map<String, Transportation> adjacentLocations = graph.get(current);

        // Eğer mevcut lokasyonun komşuları yoksa geri dön
        if (adjacentLocations == null) {
            visited.remove(current);
            return;
        }

        for (Map.Entry<String, Transportation> entry : adjacentLocations.entrySet()) {
            String nextLocation = entry.getKey();
            Transportation transportation = entry.getValue();
            // Eğer taşıma aracının çalıştığı günler arasında belirtilen tarih varsa, geçerli
            if (!transportation.getOperatingDays().contains(date.getDayOfWeek().getValue())) {
                continue;
            }
            // Eğer zaten ziyaret ettiğimiz bir lokasyona gitmek istemiyorsak, onu atla
            if (visited.contains(nextLocation)) continue;
            // Eğer uçuş ise, uçuşu bulduk olarak işaretle
            boolean newFlightFound = flightFound || transportation.getTransportationType().equals(TransportationType.FLIGHT);
            // Taşıma aracını rotaya ekle ve DFS çağır
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
