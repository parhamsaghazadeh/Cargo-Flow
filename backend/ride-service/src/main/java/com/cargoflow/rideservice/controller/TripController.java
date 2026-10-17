package com.cargoflow.rideservice.controller;

import com.cargoflow.rideservice.dto.TripRequest;
import com.cargoflow.rideservice.dto.TripResponse;
import com.cargoflow.rideservice.enums.TripStatus;
import com.cargoflow.rideservice.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> create(@Valid @RequestBody TripRequest request) {
        TripResponse response = tripService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/match/{cargoOrderId}")
    public ResponseEntity<TripResponse> createWithMatching(@PathVariable("cargoOrderId") Long cargoOrderId) {
        TripResponse response = tripService.createWithMatching(cargoOrderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getById(@PathVariable("id") Long id) {
        TripResponse response = tripService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAll() {
        List<TripResponse> responses = tripService.getAll();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TripResponse> updateStatus(@PathVariable("id") Long id, @RequestParam("status") TripStatus status) {
        TripResponse response = tripService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<TripResponse> completeTrip(@PathVariable("id") Long id) {
        TripResponse response = tripService.completeTrip(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<TripResponse>> getByDriverId(@PathVariable("driverId") Long driverId) {
        List<TripResponse> responses = tripService.getByDriverId(driverId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/cargo-order/{cargoOrderId}")
    public ResponseEntity<List<TripResponse>> getByCargoOrderId(@PathVariable("cargoOrderId") Long cargoOrderId) {
        List<TripResponse> responses = tripService.getByCargoOrderId(cargoOrderId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
