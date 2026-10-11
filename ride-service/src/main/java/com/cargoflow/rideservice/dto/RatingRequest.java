package com.cargoflow.rideservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RatingRequest {
    @NotNull
    @Positive
    private Long tripId;

    @NotNull
    @Positive
    private Long customerId;

    @NotNull
    @Positive
    private Long driverId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    @Size(max = 500)
    private String comment;

    public RatingRequest() {}

    public RatingRequest(Long tripId, Long customerId, Long driverId, Integer score, String comment) {
        this.tripId = tripId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.score = score;
        this.comment = comment;
    }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
