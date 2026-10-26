package com.cargoflow.userservice.controller;

import com.cargoflow.userservice.dto.CustomerResponse;
import com.cargoflow.userservice.dto.LoginCheckRequest;
import com.cargoflow.userservice.dto.LoginCheckResponse;
import com.cargoflow.userservice.dto.MeResponse;
import com.cargoflow.userservice.dto.SignupRequest;
import com.cargoflow.userservice.service.AuthSyncService;
import com.cargoflow.userservice.service.SignupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthSyncController {

    private final AuthSyncService authSyncService;
    private final SignupService signupService;

    public AuthSyncController(AuthSyncService authSyncService, SignupService signupService) {
        this.authSyncService = authSyncService;
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(@Valid @RequestBody SignupRequest request) {
        CustomerResponse response = signupService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/check")
    public ResponseEntity<LoginCheckResponse> checkUserExists(@Valid @RequestBody LoginCheckRequest request) {
        LoginCheckResponse response = authSyncService.checkUserExists(request.getIdentifier());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sync")
    public ResponseEntity<CustomerResponse> sync(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        CustomerResponse response = authSyncService.syncCustomer(jwt);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        CustomerResponse customer = authSyncService.syncCustomer(jwt);
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring("ROLE_".length()))
                .toList();
        String username = jwt.getClaimAsString("preferred_username");
        String keycloakId = jwt.getClaimAsString("sub");
        MeResponse response = new MeResponse(
                customer.getId(),
                customer.getName(),
                customer.getLastName(),
                customer.getAge(),
                customer.getEmail(),
                customer.getPhone(),
                username,
                keycloakId,
                roles
        );
        return ResponseEntity.ok(response);
    }
}