package com.cargoflow.truckservice.service;

import com.cargoflow.truckservice.dto.TruckRequest;
import com.cargoflow.truckservice.dto.TruckResponse;
import com.cargoflow.truckservice.enums.TruckStatus;
import java.util.List;

public interface TruckService {
    TruckResponse create(TruckRequest request);
    TruckResponse getById(Long id);
    List<TruckResponse> getAll();
    TruckResponse updateStatus(Long id, TruckStatus status);
    void delete(Long id);
    List<TruckResponse> findAvailableWithCapacity(Integer capacity);
}
