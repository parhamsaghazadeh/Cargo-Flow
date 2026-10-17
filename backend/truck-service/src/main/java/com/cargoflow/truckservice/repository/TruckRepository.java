package com.cargoflow.truckservice.repository;

import com.cargoflow.truckservice.entity.Truck;
import com.cargoflow.truckservice.enums.TruckStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    List<Truck> findByStatusAndCapacityGreaterThanEqual(TruckStatus status, Integer capacity);
    boolean existsByLicensePlate(String licensePlate);
}
