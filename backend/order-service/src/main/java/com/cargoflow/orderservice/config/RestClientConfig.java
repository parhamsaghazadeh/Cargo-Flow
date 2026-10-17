package com.cargoflow.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient truckServiceRestClient(@Value("${services.truck-service.url}") String truckServiceUrl) {
        return RestClient.builder()
                .baseUrl(truckServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean
    public RestClient userServiceRestClient(@Value("${services.user-service.url}") String userServiceUrl) {
        return RestClient.builder()
                .baseUrl(userServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
