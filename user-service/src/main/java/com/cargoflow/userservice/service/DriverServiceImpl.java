package com.cargoflow.userservice.service;

import com.cargoflow.userservice.dto.DriverRequest;
import com.cargoflow.userservice.dto.DriverResponse;
import com.cargoflow.userservice.entity.Driver;
import com.cargoflow.userservice.enums.DriverStatus;
import com.cargoflow.userservice.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public DriverResponse create(DriverRequest request) {
        if (driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new RuntimeException("Driver with license number already exists: " + request.getLicenseNumber());
        }
        Driver driver = new Driver(request.getName(), request.getLicenseNumber());
        Driver saved = driverRepository.save(driver);
        return mapToResponse(saved);
    }

    @Override
    public DriverResponse getById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        return mapToResponse(driver);
    }

    @Override
    public List<DriverResponse> getAll() {
        return driverRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DriverResponse updateStatus(Long id, DriverStatus status) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        driver.setStatus(status);
        Driver saved = driverRepository.save(driver);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new RuntimeException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }

    private DriverResponse mapToResponse(Driver driver) {
        return new DriverResponse(
                driver.getId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getStatus(),
                driver.getCreatedAt()
        );
    }
}
