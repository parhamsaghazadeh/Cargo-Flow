package com.cargoflow.rideservice.controller;

import com.cargoflow.rideservice.dto.RatingRequest;
import com.cargoflow.rideservice.dto.RatingResponse;
import com.cargoflow.rideservice.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingResponse> create(@Valid @RequestBody RatingRequest request) {
        RatingResponse response = ratingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getById(@PathVariable("id") Long id) {
        RatingResponse response = ratingService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RatingResponse>> getAll() {
        List<RatingResponse> responses = ratingService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<RatingResponse>> getByDriverId(@PathVariable("driverId") Long driverId) {
        List<RatingResponse> responses = ratingService.getByDriverId(driverId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<RatingResponse>> getByTripId(@PathVariable("tripId") Long tripId) {
        List<RatingResponse> responses = ratingService.getByTripId(tripId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RatingResponse>> getByCustomerId(@PathVariable("customerId") Long customerId) {
        List<RatingResponse> responses = ratingService.getByCustomerId(customerId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
