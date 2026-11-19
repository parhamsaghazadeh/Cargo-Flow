package com.cargoflow.userservice.controller;

import com.cargoflow.userservice.dto.CustomerRequest;
import com.cargoflow.userservice.dto.CustomerResponse;
import com.cargoflow.userservice.entity.Customer;
import com.cargoflow.userservice.repository.CustomerRepository;
import com.cargoflow.userservice.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable("id") Long id) {
        CustomerResponse response = customerService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        List<CustomerResponse> responses = customerService.getAll();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody CustomerRequest request,
                                                   Authentication authentication) {
        assertOwns(id, authentication);
        CustomerResponse response = customerService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, Authentication authentication) {
        assertOwns(id, authentication);
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void assertOwns(Long id, Authentication authentication) {
        String keycloakId = ((Jwt) authentication.getPrincipal()).getClaimAsString("sub");
        Customer customer = customerRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new AccessDeniedException("Access denied"));
        if (!customer.getId().equals(id)) {
            throw new AccessDeniedException("Access denied: cannot modify another customer's record");
        }
    }
}