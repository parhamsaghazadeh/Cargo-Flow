package com.cargoflow.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DriverRequest {
    @NotNull
    private Long customerId;

    @NotBlank
    @Size(max = 50)
    private String licenseNumber;

    public DriverRequest() {}

    public DriverRequest(Long customerId, String licenseNumber) {
        this.customerId = customerId;
        this.licenseNumber = licenseNumber;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
}
