package com.cargoflow.rideservice.entity;

import com.cargoflow.rideservice.enums.TripStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false, name = "cargo_order_id")
    private Long cargoOrderId;

    @NotNull
    @Positive
    @Column(nullable = false, name = "truck_id")
    private Long truckId;

    @NotNull
    @Positive
    @Column(nullable = false, name = "driver_id")
    private Long driverId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status = TripStatus.ASSIGNED;

    @NotNull
    @Column(nullable = false, name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @NotNull
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Trip() {}

    public Trip(Long cargoOrderId, Long truckId, Long driverId) {
        this.cargoOrderId = cargoOrderId;
        this.truckId = truckId;
        this.driverId = driverId;
        this.startedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCargoOrderId() { return cargoOrderId; }
    public void setCargoOrderId(Long cargoOrderId) { this.cargoOrderId = cargoOrderId; }
    public Long getTruckId() { return truckId; }
    public void setTruckId(Long truckId) { this.truckId = truckId; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public TripStatus getStatus() { return status; }
    public void setStatus(TripStatus status) { this.status = status; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
