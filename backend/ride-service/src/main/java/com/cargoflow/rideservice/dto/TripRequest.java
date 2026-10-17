package com.cargoflow.rideservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TripRequest {
    @NotNull
    @Positive
    private Long cargoOrderId;

    @NotNull
    @Positive
    private Long truckId;

    @NotNull
    @Positive
    private Long driverId;

    public TripRequest() {}

    public TripRequest(Long cargoOrderId, Long truckId, Long driverId) {
        this.cargoOrderId = cargoOrderId;
        this.truckId = truckId;
        this.driverId = driverId;
    }

    public Long getCargoOrderId() { return cargoOrderId; }
    public void setCargoOrderId(Long cargoOrderId) { this.cargoOrderId = cargoOrderId; }
    public Long getTruckId() { return truckId; }
    public void setTruckId(Long truckId) { this.truckId = truckId; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
}
