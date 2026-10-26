package com.cargoflow.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginCheckRequest {

    @NotBlank
    @Size(min = 3, max = 100)
    private String identifier;

    public LoginCheckRequest() {}

    public LoginCheckRequest(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
}