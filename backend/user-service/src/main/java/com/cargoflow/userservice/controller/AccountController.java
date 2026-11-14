package com.cargoflow.userservice.controller;

import com.cargoflow.userservice.dto.DeleteAccountRequest;
import com.cargoflow.userservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/delete")
    public ResponseEntity<Void> deleteAccount(@Valid @RequestBody DeleteAccountRequest request,
                                              Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String keycloakId = jwt.getClaimAsString("sub");
        String username = jwt.getClaimAsString("preferred_username");

        accountService.deleteAccount(keycloakId, username, request.getPassword());
        return ResponseEntity.noContent().build();
    }
}
