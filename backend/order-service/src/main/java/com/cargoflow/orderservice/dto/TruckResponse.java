package com.cargoflow.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class TruckResponse {
    private Long id;
    private String licensePlate;
    private Integer capacity;
    private Boolean refrigerated;
    private String status;
    private LocalDateTime createdAt;

    public TruckResponse() {}

    public TruckResponse(Long id, String licensePlate, Integer capacity, Boolean refrigerated, String status, LocalDateTime createdAt) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.refrigerated = refrigerated;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Boolean getRefrigerated() { return refrigerated; }
    public void setRefrigerated(Boolean refrigerated) { this.refrigerated = refrigerated; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
