package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.common.dto.AuthResponse;
import com.fowobi.common.dto.LoginRequest;
import com.fowobi.common.dto.RegisterRequest;
import com.fowobi.security.AuthService;
import com.fowobi.security.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Controller Test")
class AuthControllerTest {

    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;

    @Test
    @DisplayName("Login Success")
    void loginSuccess() {
        LoginRequest request = new LoginRequest("user", "pass");

        when(authService.login(request)).thenReturn(ApiResponse.ok(new AuthResponse()));

        ApiResponse<AuthResponse> loginResponse = authController.login(request);

        assertEquals(200, loginResponse.getCode());

        verify(authService, times(1)).login(request);
    }

    @Test
    @DisplayName("Register Success")
    void registerSuccess() {
        RegisterRequest request = new RegisterRequest("user", "pass");

        ResponseEntity<Void> registerResponse = authController.register(request);

        assertNotNull(registerResponse);
        assertEquals(HttpStatusCode.valueOf(201), registerResponse.getStatusCode());
    }
}