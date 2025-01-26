package com.alifetvaci.travelmanagementservice.controller;

import com.alifetvaci.travelmanagementservice.api.BaseApiResponse;
import com.alifetvaci.travelmanagementservice.api.BaseController;
import com.alifetvaci.travelmanagementservice.controller.request.LocationRequest;
import com.alifetvaci.travelmanagementservice.controller.response.LocationResponse;
import com.alifetvaci.travelmanagementservice.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController extends BaseController {

    private final LocationService service;

    @PostMapping("/v1/location")
    public BaseApiResponse<LocationResponse> createLocation(@Valid @RequestBody LocationRequest request) {
        return success(service.createLocation(request));
    }

    @GetMapping("/v1/location/{id}")
    public BaseApiResponse<LocationResponse> getLocation(@PathVariable(value="id") Long id) {
        return success(service.getLocationById(id));
    }

    @GetMapping("/v1/location")
    public BaseApiResponse<List<LocationResponse>> getLocations() {
        return success(service.getAllLocations());
    }


    @PutMapping("/v1/location/{id}")
    public BaseApiResponse<LocationResponse> updateLocation(@PathVariable(value="id") Long id, @Valid @RequestBody   LocationRequest request) {
        return success(service.updateLocation(id, request));
    }

    @DeleteMapping("/v1/location/{id}")
    public BaseApiResponse<Void> deleteLocation(@PathVariable(value="id") Long id) {
        service.deleteLocation(id);
        return success(null);
    }
}