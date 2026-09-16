package com.cargoflow.truckservice.entity;

import com.cargoflow.truckservice.enums.TruckStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String licensePlate;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer capacity;

    @NotNull
    @Column(nullable = false)
    private Boolean refrigerated = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TruckStatus status = TruckStatus.AVAILABLE;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Truck() {}

    public Truck(String licensePlate, Integer capacity, Boolean refrigerated) {
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.refrigerated = refrigerated;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Boolean getRefrigerated() { return refrigerated; }
    public void setRefrigerated(Boolean refrigerated) { this.refrigerated = refrigerated; }
    public TruckStatus getStatus() { return status; }
    public void setStatus(TruckStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
