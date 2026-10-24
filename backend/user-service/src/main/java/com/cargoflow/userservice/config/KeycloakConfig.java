package com.cargoflow.userservice.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakConfig {

    private final KeycloakProperties properties;

    public KeycloakConfig(KeycloakProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .realm("master")
                .clientId(properties.getAdminClientId())
                .clientSecret(properties.getAdminClientSecret())
                .username(properties.getAdminUsername())
                .password(properties.getAdminPassword())
                .build();
    }

    public KeycloakProperties getProperties() {
        return properties;
    }
}