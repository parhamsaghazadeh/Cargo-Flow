package com.cargoflow.truckservice.controller;

import com.cargoflow.truckservice.dto.TruckRequest;
import com.cargoflow.truckservice.dto.TruckResponse;
import com.cargoflow.truckservice.enums.TruckStatus;
import com.cargoflow.truckservice.service.TruckService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @PostMapping
    public ResponseEntity<TruckResponse> create(@Valid @RequestBody TruckRequest request) {
        TruckResponse response = truckService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckResponse> getById(@PathVariable Long id) {
        TruckResponse response = truckService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TruckResponse>> getAll() {
        List<TruckResponse> responses = truckService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TruckResponse>> getAvailableWithCapacity(@RequestParam Integer capacity) {
        List<TruckResponse> responses = truckService.findAvailableWithCapacity(capacity);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TruckResponse> updateStatus(@PathVariable Long id, @RequestParam TruckStatus status) {
        TruckResponse response = truckService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        truckService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
