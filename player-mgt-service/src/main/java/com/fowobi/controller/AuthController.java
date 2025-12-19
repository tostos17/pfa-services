package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.common.dto.AuthResponse;
import com.fowobi.common.dto.LoginRequest;
import com.fowobi.common.dto.RegisterRequest;
import com.fowobi.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fowobi.utils.AppConstants.BASE_CONTEXT;

@RestController
@RequestMapping(BASE_CONTEXT + "/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return service.login(request);
    }
}

