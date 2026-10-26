package com.cargoflow.userservice.service;

import com.cargoflow.userservice.config.KeycloakProperties;
import com.cargoflow.userservice.dto.CustomerResponse;
import com.cargoflow.userservice.dto.LoginCheckResponse;
import com.cargoflow.userservice.entity.Customer;
import com.cargoflow.userservice.repository.CustomerRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthSyncServiceImpl implements AuthSyncService {

    private static final String KEYCLOAK_ID_CLAIM = "sub";
    private static final String GIVEN_NAME_CLAIM = "given_name";
    private static final String FAMILY_NAME_CLAIM = "family_name";
    private static final String EMAIL_CLAIM = "email";
    private static final String DEFAULT_PHONE = "-";

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final CustomerRepository customerRepository;

    public AuthSyncServiceImpl(Keycloak keycloak, KeycloakProperties keycloakProperties,
                               CustomerRepository customerRepository) {
        this.keycloak = keycloak;
        this.keycloakProperties = keycloakProperties;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerResponse syncCustomer(Jwt jwt) {
        String keycloakId = jwt.getClaimAsString(KEYCLOAK_ID_CLAIM);
        String email = jwt.getClaimAsString(EMAIL_CLAIM);
        String name = jwt.getClaimAsString(GIVEN_NAME_CLAIM);
        String lastName = jwt.getClaimAsString(FAMILY_NAME_CLAIM);

        Optional<Customer> existing = customerRepository.findByKeycloakId(keycloakId);
        Customer customer = existing.orElseGet(() ->
                customerRepository.save(new Customer(keycloakId, name, lastName, null, email, DEFAULT_PHONE)));

        return mapToResponse(customer);
    }

    @Override
    public LoginCheckResponse checkUserExists(String identifier) {
        UsersResource users = keycloak.realm(keycloakProperties.getRealm()).users();

        List<UserRepresentation> byUsername = users.searchByUsername(identifier, true);
        List<UserRepresentation> byEmail = users.searchByEmail(identifier, true);

        UserRepresentation keycloakUser = byUsername.stream().findFirst()
                .orElseGet(() -> byEmail.stream().findFirst().orElse(null));

        if (keycloakUser == null) {
            return new LoginCheckResponse(false, "User does not exist in Keycloak");
        }

        String email = keycloakUser.getEmail();
        if (email == null || email.isBlank()) {
            email = identifier;
        }

        String finalEmail = email;
        boolean inDatabase = customerRepository.findByEmail(finalEmail).isPresent();
        if (!inDatabase) {
            return new LoginCheckResponse(false, "User does not exist in the local database");
        }

        return new LoginCheckResponse(true, "User exists in both Keycloak and the local database");
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getLastName(),
                customer.getAge(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt()
        );
    }
}