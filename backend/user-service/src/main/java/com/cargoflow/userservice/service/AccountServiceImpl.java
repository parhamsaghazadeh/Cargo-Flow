package com.cargoflow.userservice.service;

import com.cargoflow.userservice.config.KeycloakProperties;
import com.cargoflow.userservice.entity.Customer;
import com.cargoflow.userservice.entity.Driver;
import com.cargoflow.userservice.exception.BusinessRuleViolationException;
import com.cargoflow.userservice.exception.InvalidPasswordException;
import com.cargoflow.userservice.exception.ResourceNotFoundException;
import com.cargoflow.userservice.repository.CustomerRepository;
import com.cargoflow.userservice.repository.DriverRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;

    public AccountServiceImpl(Keycloak keycloak, KeycloakProperties keycloakProperties,
                              CustomerRepository customerRepository, DriverRepository driverRepository) {
        this.keycloak = keycloak;
        this.keycloakProperties = keycloakProperties;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public void deleteAccount(String keycloakId, String username, String password) {
        verifyPassword(username, password);

        Customer customer = customerRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for keycloak id: " + keycloakId));

        List<Driver> drivers = driverRepository.findByCustomer(customer);
        if (!drivers.isEmpty()) {
            driverRepository.deleteAll(drivers);
        }
        customerRepository.delete(customer);

        deleteUserFromKeycloak(keycloakId);
    }

    private void verifyPassword(String username, String password) {
        String tokenUrl = keycloakProperties.getServerUrl()
                + "/realms/" + keycloakProperties.getRealm() + "/protocol/openid-connect/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "cargo-flow-ui");
        body.add("username", username);
        body.add("password", password);

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(tokenUrl, body, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new InvalidPasswordException("Invalid current password");
            }
            throw e;
        }
    }

    private void deleteUserFromKeycloak(String keycloakId) {
        RealmResource realm = keycloak.realm(keycloakProperties.getRealm());
        UsersResource users = realm.users();
        try {
            UserRepresentation existing = users.get(keycloakId).toRepresentation();
            if (existing != null) {
                users.delete(keycloakId);
            }
        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new BusinessRuleViolationException("Keycloak user not found: " + keycloakId);
        }
    }
}