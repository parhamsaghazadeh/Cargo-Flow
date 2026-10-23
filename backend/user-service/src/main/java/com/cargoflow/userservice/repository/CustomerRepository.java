package com.cargoflow.userservice.repository;

import com.cargoflow.userservice.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByKeycloakId(String keycloakId);

    Optional<Customer> findByEmail(String email);
}
