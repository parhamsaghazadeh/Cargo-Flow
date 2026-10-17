package com.cargoflow.rideservice.dto;

import java.time.LocalDateTime;

public class RatingResponse {
    private Long id;
    private Long tripId;
    private Long customerId;
    private Long driverId;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;

    public RatingResponse() {}

    public RatingResponse(Long id, Long tripId, Long customerId, Long driverId, Integer score, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.tripId = tripId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
