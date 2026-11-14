package com.cargoflow.userservice.service;

public interface AccountService {

    void deleteAccount(String keycloakId, String username, String password);
}