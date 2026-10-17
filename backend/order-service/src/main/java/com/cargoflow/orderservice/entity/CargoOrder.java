package com.cargoflow.orderservice.entity;

import com.cargoflow.orderservice.enums.CargoOrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "cargo_orders")
public class CargoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false, name = "customer_id")
    private Long customerId;

    @NotBlank
    @Column(nullable = false)
    private String origin;

    @NotBlank
    @Column(nullable = false)
    private String destination;

    @NotBlank
    @Column(nullable = false)
    private String cargoType;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer cargoWeight;

    @NotNull
    @Column(nullable = false)
    private Boolean refrigeratedRequired = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoOrderStatus status = CargoOrderStatus.PENDING;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public CargoOrder() {}

    public CargoOrder(Long customerId, String origin, String destination, String cargoType, Integer cargoWeight, Boolean refrigeratedRequired) {
        this.customerId = customerId;
        this.origin = origin;
        this.destination = destination;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;
        this.refrigeratedRequired = refrigeratedRequired;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getCargoType() { return cargoType; }
    public void setCargoType(String cargoType) { this.cargoType = cargoType; }
    public Integer getCargoWeight() { return cargoWeight; }
    public void setCargoWeight(Integer cargoWeight) { this.cargoWeight = cargoWeight; }
    public Boolean getRefrigeratedRequired() { return refrigeratedRequired; }
    public void setRefrigeratedRequired(Boolean refrigeratedRequired) { this.refrigeratedRequired = refrigeratedRequired; }
    public CargoOrderStatus getStatus() { return status; }
    public void setStatus(CargoOrderStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
