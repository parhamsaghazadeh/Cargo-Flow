package com.cargoflow.orderservice.service;

import com.cargoflow.orderservice.client.TruckClient;
import com.cargoflow.orderservice.client.UserClient;
import com.cargoflow.orderservice.dto.CargoOrderRequest;
import com.cargoflow.orderservice.dto.CargoOrderResponse;
import com.cargoflow.orderservice.dto.DriverResponse;
import com.cargoflow.orderservice.dto.TruckResponse;
import com.cargoflow.orderservice.entity.CargoOrder;
import com.cargoflow.orderservice.enums.CargoOrderStatus;
import com.cargoflow.orderservice.exception.ResourceNotFoundException;
import com.cargoflow.orderservice.exception.BusinessRuleViolationException;
import com.cargoflow.orderservice.repository.CargoOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CargoOrderServiceImpl implements CargoOrderService {

    private final CargoOrderRepository cargoOrderRepository;
    private final TruckClient truckClient;
    private final UserClient userClient;

    public CargoOrderServiceImpl(CargoOrderRepository cargoOrderRepository,
                                  TruckClient truckClient,
                                  UserClient userClient) {
        this.cargoOrderRepository = cargoOrderRepository;
        this.truckClient = truckClient;
        this.userClient = userClient;
    }

    @Override
    @Transactional
    public CargoOrderResponse create(CargoOrderRequest request) {
        CargoOrder cargoOrder = new CargoOrder(
                request.getCustomerId(),
                request.getOrigin(),
                request.getDestination(),
                request.getCargoType(),
                request.getCargoWeight(),
                request.getRefrigeratedRequired()
        );
        CargoOrder saved = cargoOrderRepository.save(cargoOrder);
        return mapToResponse(saved);
    }

    @Override
    public CargoOrderResponse getById(Long id) {
        CargoOrder cargoOrder = cargoOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CargoOrder not found with id: " + id));
        return mapToResponse(cargoOrder);
    }

    @Override
    public List<CargoOrderResponse> getAll() {
        return cargoOrderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CargoOrderResponse> getByStatus(CargoOrderStatus status) {
        return cargoOrderRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CargoOrderResponse updateStatus(Long id, CargoOrderStatus status) {
        CargoOrder cargoOrder = cargoOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CargoOrder not found with id: " + id));
        cargoOrder.setStatus(status);
        CargoOrder saved = cargoOrderRepository.save(cargoOrder);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!cargoOrderRepository.existsById(id)) {
            throw new ResourceNotFoundException("CargoOrder not found with id: " + id);
        }
        cargoOrderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CargoOrderResponse assignTruckAndDriver(Long cargoOrderId) {
        CargoOrder cargoOrder = cargoOrderRepository.findById(cargoOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("CargoOrder not found with id: " + cargoOrderId));

        if (cargoOrder.getStatus() != CargoOrderStatus.PENDING) {
            throw new BusinessRuleViolationException("CargoOrder must be in PENDING status to assign truck and driver");
        }

        // Find available truck with sufficient capacity
        List<TruckResponse> availableTrucks = truckClient.findAvailableWithCapacity(cargoOrder.getCargoWeight());

        TruckResponse selectedTruck = availableTrucks.stream()
                .filter(truck -> "AVAILABLE".equals(truck.getStatus()))
                .filter(truck -> truck.getCapacity() >= cargoOrder.getCargoWeight())
                .filter(truck -> {
                    // If refrigeration is required, truck must be refrigerated
                    if (cargoOrder.getRefrigeratedRequired()) {
                        return Boolean.TRUE.equals(truck.getRefrigerated());
                    }
                    return true;
                })
                .findFirst()
                .orElseThrow(() -> new BusinessRuleViolationException("No suitable truck found for cargo order"));

        // Find available driver
        List<DriverResponse> availableDrivers = userClient.getAllDrivers();
        DriverResponse selectedDriver = availableDrivers.stream()
                .filter(driver -> "AVAILABLE".equals(driver.getStatus()))
                .findFirst()
                .orElseThrow(() -> new BusinessRuleViolationException("No available driver found"));

        // Update truck status to BUSY
        truckClient.updateStatus(selectedTruck.getId(), "BUSY");

        // Update driver status to BUSY
        userClient.updateDriverStatus(selectedDriver.getId(), "BUSY");

        // Update cargo order status to ASSIGNED
        cargoOrder.setStatus(CargoOrderStatus.ASSIGNED);
        CargoOrder saved = cargoOrderRepository.save(cargoOrder);

        return mapToResponse(saved);
    }

    private CargoOrderResponse mapToResponse(CargoOrder cargoOrder) {
        return new CargoOrderResponse(
                cargoOrder.getId(),
                cargoOrder.getCustomerId(),
                cargoOrder.getOrigin(),
                cargoOrder.getDestination(),
                cargoOrder.getCargoType(),
                cargoOrder.getCargoWeight(),
                cargoOrder.getRefrigeratedRequired(),
                cargoOrder.getStatus(),
                cargoOrder.getCreatedAt()
        );
    }
}
