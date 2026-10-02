package com.cargoflow.orderservice.controller;

import com.cargoflow.orderservice.dto.CargoOrderRequest;
import com.cargoflow.orderservice.dto.CargoOrderResponse;
import com.cargoflow.orderservice.enums.CargoOrderStatus;
import com.cargoflow.orderservice.service.CargoOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cargo-orders")
public class CargoOrderController {

    private final CargoOrderService cargoOrderService;

    public CargoOrderController(CargoOrderService cargoOrderService) {
        this.cargoOrderService = cargoOrderService;
    }

    @PostMapping
    public ResponseEntity<CargoOrderResponse> create(@Valid @RequestBody CargoOrderRequest request) {
        CargoOrderResponse response = cargoOrderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoOrderResponse> getById(@PathVariable("id") Long id) {
        CargoOrderResponse response = cargoOrderService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CargoOrderResponse>> getAll() {
        List<CargoOrderResponse> responses = cargoOrderService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CargoOrderResponse>> getByStatus(@PathVariable("status") CargoOrderStatus status) {
        List<CargoOrderResponse> responses = cargoOrderService.getByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CargoOrderResponse> updateStatus(@PathVariable("id") Long id, @RequestParam("status") CargoOrderStatus status) {
        CargoOrderResponse response = cargoOrderService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<CargoOrderResponse> assignTruckAndDriver(@PathVariable("id") Long id) {
        CargoOrderResponse response = cargoOrderService.assignTruckAndDriver(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        cargoOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
