package com.cargoflow.userservice.service;

import com.cargoflow.userservice.dto.CustomerResponse;
import com.cargoflow.userservice.dto.SignupRequest;

public interface SignupService {

    CustomerResponse signup(SignupRequest request);
}