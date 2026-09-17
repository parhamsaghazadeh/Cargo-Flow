package com.cargoflow.truckservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TruckRequest {
    @NotBlank
    @Size(max = 20)
    private String licensePlate;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    private Boolean refrigerated;

    public TruckRequest() {}

    public TruckRequest(String licensePlate, Integer capacity, Boolean refrigerated) {
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.refrigerated = refrigerated;
    }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Boolean getRefrigerated() { return refrigerated; }
    public void setRefrigerated(Boolean refrigerated) { this.refrigerated = refrigerated; }
}
