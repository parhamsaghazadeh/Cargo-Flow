package com.cargoflow.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CargoOrderRequest {
    @NotNull
    @Positive
    private Long customerId;

    @NotBlank
    @Size(max = 200)
    private String origin;

    @NotBlank
    @Size(max = 200)
    private String destination;

    @NotBlank
    @Size(max = 100)
    private String cargoType;

    @NotNull
    @Positive
    private Integer cargoWeight;

    @NotNull
    private Boolean refrigeratedRequired;

    public CargoOrderRequest() {}

    public CargoOrderRequest(Long customerId, String origin, String destination, String cargoType, Integer cargoWeight, Boolean refrigeratedRequired) {
        this.customerId = customerId;
        this.origin = origin;
        this.destination = destination;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;
        this.refrigeratedRequired = refrigeratedRequired;
    }

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
}
