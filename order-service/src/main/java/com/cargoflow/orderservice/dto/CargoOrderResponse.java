package com.cargoflow.orderservice.dto;

import com.cargoflow.orderservice.enums.CargoOrderStatus;
import java.time.LocalDateTime;

public class CargoOrderResponse {
    private Long id;
    private Long customerId;
    private String origin;
    private String destination;
    private String cargoType;
    private Integer cargoWeight;
    private Boolean refrigeratedRequired;
    private CargoOrderStatus status;
    private LocalDateTime createdAt;

    public CargoOrderResponse() {}

    public CargoOrderResponse(Long id, Long customerId, String origin, String destination, String cargoType,
                              Integer cargoWeight, Boolean refrigeratedRequired,
                              CargoOrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.origin = origin;
        this.destination = destination;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;
        this.refrigeratedRequired = refrigeratedRequired;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getCargoType() { return cargoType; }
    public void setCargoType(String cargoType) { this.cargoType = cargoType; }
    public Integer getCargoWeight() { return cargoWeight; }
    public void setCargoWeight(Integer cargoWeight) { this.cargoWeight = cargoWeight; }
    public Boolean getRefrigeratedRequired() { return refrigeratedRequired; }
    public void setRefrigeratedRequired(Boolean refrigeratedRequired) { this.refrigeratedRequired = refrigeratedRequired; }
    public CargoOrderStatus getStatus() { return status; }
    public void setStatus(CargoOrderStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
