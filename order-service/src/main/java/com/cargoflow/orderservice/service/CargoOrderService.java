package com.cargoflow.orderservice.service;

import com.cargoflow.orderservice.dto.CargoOrderRequest;
import com.cargoflow.orderservice.dto.CargoOrderResponse;
import com.cargoflow.orderservice.enums.CargoOrderStatus;
import java.util.List;

public interface CargoOrderService {
    CargoOrderResponse create(CargoOrderRequest request);
    CargoOrderResponse getById(Long id);
    List<CargoOrderResponse> getAll();
    List<CargoOrderResponse> getByStatus(CargoOrderStatus status);
    CargoOrderResponse updateStatus(Long id, CargoOrderStatus status);
    void delete(Long id);
    CargoOrderResponse assignTruckAndDriver(Long cargoOrderId);
}
