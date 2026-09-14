package com.cargoflow.userservice.dto;

import com.cargoflow.userservice.enums.DriverStatus;
import java.time.LocalDateTime;

public class DriverResponse {
    private Long id;
    private String name;
    private String licenseNumber;
    private DriverStatus status;
    private LocalDateTime createdAt;

    public DriverResponse() {}

    public DriverResponse(Long id, String name, String licenseNumber, DriverStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public DriverStatus getStatus() { return status; }
    public void setStatus(DriverStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
