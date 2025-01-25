package com.alifetvaci.travelmanagementservice.controller;


import com.alifetvaci.travelmanagementservice.api.BaseApiResponse;
import com.alifetvaci.travelmanagementservice.api.BaseController;
import com.alifetvaci.travelmanagementservice.controller.request.TransportationRequest;
import com.alifetvaci.travelmanagementservice.controller.response.TransportationResponse;
import com.alifetvaci.travelmanagementservice.service.TransportationService;
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
public class TransportationController extends BaseController {

    private final TransportationService service;

    @PostMapping("/v1/transportation")
    public BaseApiResponse<TransportationResponse> createTransportation(@RequestBody @Valid TransportationRequest request) {
        return success(service.createTransportation(request));
    }

    @GetMapping("/v1/transportation/{id}")
    public BaseApiResponse<TransportationResponse> getTransportationById(@PathVariable(value="id") Long id) {
        return success(service.getTransportationById(id));
    }

    @GetMapping("/v1/transportation")
    public BaseApiResponse<List<TransportationResponse>> getAllTransportations() {
        return success(service.getAllTransportations());
    }

    @PutMapping("/v1/transportation/{id}")
    public BaseApiResponse<TransportationResponse> updateTransportation(@PathVariable(value="id") Long id, @RequestBody  @Valid TransportationRequest request) {
        return success(service.updateTransportation(id, request));
    }

    @DeleteMapping("/v1/transportation/{id}")
    public BaseApiResponse<Void> deleteTransportation(@PathVariable(value="id") Long id) {
        service.deleteTransportation(id);
        return success(null);
    }
}
