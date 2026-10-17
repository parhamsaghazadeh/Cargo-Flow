package com.cargoflow.orderservice.client;

import com.cargoflow.orderservice.dto.DriverResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(@Qualifier("userServiceRestClient") RestClient userServiceRestClient) {
        this.restClient = userServiceRestClient;
    }

    public List<DriverResponse> getAllDrivers() {
        try {
            return restClient.get()
                    .uri("/api/v1/drivers")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<DriverResponse>>() {});
        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return List.of();
            }
            throw new RuntimeException("Failed to call user-service: " + e.getMessage(), e);
        }
    }

    public DriverResponse getDriverById(Long driverId) {
        try {
            return restClient.get()
                    .uri("/api/v1/drivers/{id}", driverId)
                    .retrieve()
                    .body(DriverResponse.class);
        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw new RuntimeException("Failed to get driver: " + e.getMessage(), e);
        }
    }

    public void updateDriverStatus(Long driverId, String status) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/drivers/{id}/status")
                            .queryParam("status", status)
                            .build(driverId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Failed to update driver status: " + e.getMessage(), e);
        }
    }
}
