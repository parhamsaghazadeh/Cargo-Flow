package com.cargoflow.rideservice.dto;

import com.cargoflow.rideservice.enums.TripStatus;
import java.time.LocalDateTime;

public class TripResponse {
    private Long id;
    private Long cargoOrderId;
    private Long truckId;
    private Long driverId;
    private TripStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;

    public TripResponse() {}

    public TripResponse(Long id, Long cargoOrderId, Long truckId, Long driverId, TripStatus status, LocalDateTime startedAt, LocalDateTime completedAt, LocalDateTime createdAt) {
        this.id = id;
        this.cargoOrderId = cargoOrderId;
        this.truckId = truckId;
        this.driverId = driverId;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
    }

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
