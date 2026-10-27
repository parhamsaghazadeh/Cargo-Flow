package com.cargoflow.userservice.dto;

public class LoginCheckResponse {

    private boolean exists;
    private String message;

    public LoginCheckResponse() {}

    public LoginCheckResponse(boolean exists, String message) {
        this.exists = exists;
        this.message = message;
    }

    public boolean isExists() { return exists; }
    public void setExists(boolean exists) { this.exists = exists; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}