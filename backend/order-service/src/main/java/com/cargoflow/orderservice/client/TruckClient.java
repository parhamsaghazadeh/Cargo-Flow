package com.cargoflow.orderservice.client;

import com.cargoflow.orderservice.dto.TruckResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
public class TruckClient {

    private final RestClient restClient;

    public TruckClient(@Qualifier("truckServiceRestClient") RestClient truckServiceRestClient) {
        this.restClient = truckServiceRestClient;
    }

    public List<TruckResponse> findAvailableWithCapacity(Integer capacity) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/trucks/available")
                            .queryParam("capacity", capacity)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<TruckResponse>>() {});
        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return List.of();
            }
            throw new RuntimeException("Failed to call truck-service: " + e.getMessage(), e);
        }
    }

    public void updateStatus(Long truckId, String status) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/trucks/{id}/status")
                            .queryParam("status", status)
                            .build(truckId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Failed to update truck status: " + e.getMessage(), e);
        }
    }
}
