package com.cargoflow.userservice.dto;

import com.cargoflow.userservice.enums.DriverStatus;
import java.time.LocalDateTime;

public class DriverResponse {
    private Long id;
    private Long customerId;
    private String customerName;
    private String licenseNumber;
    private DriverStatus status;
    private LocalDateTime createdAt;

    public DriverResponse() {}

    public DriverResponse(Long id, Long customerId, String customerName, String licenseNumber, DriverStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public DriverStatus getStatus() { return status; }
    public void setStatus(DriverStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
