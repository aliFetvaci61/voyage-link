package com.alifetvaci.travelmanagementservice.controller;


import com.alifetvaci.travelmanagementservice.api.BaseApiResponse;
import com.alifetvaci.travelmanagementservice.api.BaseController;
import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.repository.model.Transportation;
import com.alifetvaci.travelmanagementservice.service.RouteService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RouteController extends BaseController {
    private final RouteService routeService;

    @GetMapping("/v1/routes")
    public BaseApiResponse<List<List<TransportationResponse>>> getRoutes(
            @RequestParam
            @Pattern(regexp = "^[A-Z]{3,}$", message = "Origin must contain at least 3 uppercase letters and only uppercase letters")
            String origin,
            @RequestParam
            @Pattern(regexp = "^[A-Z]{3,}$", message = "Destination must contain at least 3 uppercase letters and only uppercase letters")
            String destination,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return success(routeService.findRoutes(origin, destination, date));
    }
}