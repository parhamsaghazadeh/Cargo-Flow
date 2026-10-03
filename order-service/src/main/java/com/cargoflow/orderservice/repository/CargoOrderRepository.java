package com.cargoflow.orderservice.repository;

import com.cargoflow.orderservice.entity.CargoOrder;
import com.cargoflow.orderservice.enums.CargoOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoOrderRepository extends JpaRepository<CargoOrder, Long> {
    List<CargoOrder> findByStatus(CargoOrderStatus status);
}
