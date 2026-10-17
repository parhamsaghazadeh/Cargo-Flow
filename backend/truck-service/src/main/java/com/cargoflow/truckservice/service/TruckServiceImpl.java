package com.cargoflow.truckservice.service;

import com.cargoflow.truckservice.dto.TruckRequest;
import com.cargoflow.truckservice.dto.TruckResponse;
import com.cargoflow.truckservice.entity.Truck;
import com.cargoflow.truckservice.enums.TruckStatus;
import com.cargoflow.truckservice.exception.BusinessRuleViolationException;
import com.cargoflow.truckservice.exception.ResourceNotFoundException;
import com.cargoflow.truckservice.repository.TruckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TruckServiceImpl implements TruckService {

    private final TruckRepository truckRepository;

    public TruckServiceImpl(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    @Transactional
    public TruckResponse create(TruckRequest request) {
        if (truckRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new BusinessRuleViolationException("Truck with license plate already exists: " + request.getLicensePlate());
        }
        Truck truck = new Truck(request.getLicensePlate(), request.getCapacity(), request.getRefrigerated());
        Truck saved = truckRepository.save(truck);
        return mapToResponse(saved);
    }

    @Override
    public TruckResponse getById(Long id) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found with id: " + id));
        return mapToResponse(truck);
    }

    @Override
    public List<TruckResponse> getAll() {
        return truckRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TruckResponse updateStatus(Long id, TruckStatus status) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found with id: " + id));
        truck.setStatus(status);
        Truck saved = truckRepository.save(truck);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!truckRepository.existsById(id)) {
            throw new ResourceNotFoundException("Truck not found with id: " + id);
        }
        truckRepository.deleteById(id);
    }

    @Override
    public List<TruckResponse> findAvailableWithCapacity(Integer capacity) {
        return truckRepository.findByStatusAndCapacityGreaterThanEqual(TruckStatus.AVAILABLE, capacity).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TruckResponse mapToResponse(Truck truck) {
        return new TruckResponse(
                truck.getId(),
                truck.getLicensePlate(),
                truck.getCapacity(),
                truck.getRefrigerated(),
                truck.getStatus(),
                truck.getCreatedAt()
        );
    }
}
