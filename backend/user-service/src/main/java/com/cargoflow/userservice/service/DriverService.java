package com.cargoflow.userservice.service;

import com.cargoflow.userservice.dto.DriverRequest;
import com.cargoflow.userservice.dto.DriverResponse;
import java.util.List;

public interface DriverService {
    DriverResponse create(DriverRequest request);
    DriverResponse getById(Long id);
    List<DriverResponse> getAll();
    DriverResponse updateStatus(Long id, com.cargoflow.userservice.enums.DriverStatus status);
    void delete(Long id);
}
