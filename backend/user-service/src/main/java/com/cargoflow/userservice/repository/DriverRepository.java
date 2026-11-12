package com.cargoflow.userservice.repository;

import com.cargoflow.userservice.entity.Customer;
import com.cargoflow.userservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByLicenseNumber(String licenseNumber);
    List<Driver> findByCustomer(Customer customer);
}
