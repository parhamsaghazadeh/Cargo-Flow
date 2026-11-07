package com.cargoflow.userservice.service;

import com.cargoflow.userservice.config.KeycloakProperties;
import com.cargoflow.userservice.dto.CustomerResponse;
import com.cargoflow.userservice.dto.SignupRequest;
import com.cargoflow.userservice.entity.Customer;
import com.cargoflow.userservice.exception.BusinessRuleViolationException;
import com.cargoflow.userservice.repository.CustomerRepository;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SignupServiceImpl implements SignupService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final CustomerRepository customerRepository;

    public SignupServiceImpl(Keycloak keycloak, KeycloakProperties keycloakProperties,
                             CustomerRepository customerRepository) {
        this.keycloak = keycloak;
        this.keycloakProperties = keycloakProperties;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerResponse signup(SignupRequest request) {
        UsersResource users = keycloak.realm(keycloakProperties.getRealm()).users();

        if (!users.searchByUsername(request.getUsername(), true).isEmpty()) {
            throw new BusinessRuleViolationException("Username already taken: " + request.getUsername());
        }
        if (!users.searchByEmail(request.getEmail(), true).isEmpty()) {
            throw new BusinessRuleViolationException("Email already registered: " + request.getEmail());
        }
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessRuleViolationException("Email already registered: " + request.getEmail());
        }

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);
        user.setCredentials(java.util.List.of(credential));

        String keycloakId;
        try (Response createResponse = users.create(user)) {
            if (createResponse.getStatus() != 201) {
                throw new BusinessRuleViolationException("Failed to create user in Keycloak: HTTP " + createResponse.getStatus());
            }
            keycloakId = extractKeycloakId(createResponse.getLocation().getPath());
        }

        Customer customer = new Customer(keycloakId, request.getFirstName(), request.getLastName(),
                request.getAge(), request.getEmail(), request.getPhone());
        Customer saved = customerRepository.save(customer);
        return mapToResponse(saved);
    }

    private String extractKeycloakId(String locationPath) {
        String[] segments = locationPath.split("/");
        return segments[segments.length - 1];
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getLastName(),
                customer.getAge(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getDateOfBirth(),
                customer.getAddress(),
                customer.getCreatedAt()
        );
    }
}