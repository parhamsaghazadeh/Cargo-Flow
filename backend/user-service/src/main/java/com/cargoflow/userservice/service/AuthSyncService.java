package com.cargoflow.userservice.service;

import com.cargoflow.userservice.dto.CustomerResponse;
import com.cargoflow.userservice.dto.LoginCheckResponse;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthSyncService {

    CustomerResponse syncCustomer(Jwt jwt);

    LoginCheckResponse checkUserExists(String identifier);
}