package com.cargoflow.userservice.controller;

import com.cargoflow.userservice.dto.DriverRequest;
import com.cargoflow.userservice.dto.DriverResponse;
import com.cargoflow.userservice.enums.DriverStatus;
import com.cargoflow.userservice.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponse> create(@Valid @RequestBody DriverRequest request) {
        DriverResponse response = driverService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable Long id) {
        DriverResponse response = driverService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAll() {
        List<DriverResponse> responses = driverService.getAll();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DriverResponse> updateStatus(@PathVariable Long id, @RequestParam DriverStatus status) {
        DriverResponse response = driverService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
