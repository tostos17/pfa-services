package com.fowobi.security;

import com.fowobi.api.ApiResponse;
import com.fowobi.common.dto.AppUser;
import com.fowobi.common.dto.AuthResponse;
import com.fowobi.common.dto.LoginRequest;
import com.fowobi.common.dto.RegisterRequest;
import com.fowobi.repository.AppUserRepository;
import com.fowobi.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Authentication Service Tests")
class AuthServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthService authService;

    private AppUser appUser;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setup() {
        appUser = new AppUser();
        appUser.setUserId("userId");

        registerRequest = new RegisterRequest("user", "pass");

        loginRequest = new LoginRequest("user", "pass");
    }

    @Test
    @DisplayName("Register Success")
    void registerSuccess() {
        when(appUserRepository.save(any())).thenReturn(new AppUser());
        authService.register(registerRequest);

        verify(appUserRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Register Fail When User Exists")
    void registerShouldThrowRuntimeException() {
        when(appUserRepository.existsByUsername(anyString())).thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> authService.register(registerRequest)
        );

        assertEquals("Username already exists", ex.getMessage());
        verify(appUserRepository, times(1)).existsByUsername(anyString());
    }

    @Test
    @DisplayName("Register Fails When Unable to Save User")
    void registerShouldFail_whenUserAlreadyExists() {
        when(appUserRepository.save(any())).thenThrow(new RuntimeException("Unable to add new user"));
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,
                () -> authService.register(registerRequest)
        );

        assertEquals("Unable to add new user", runtimeException.getMessage());

        verify(appUserRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Login Success")
    void loginSuccess() {
        when(jwtUtils.generateToken(anyString())).thenReturn("accessToken");

        ApiResponse<AuthResponse> response = authService.login(loginRequest);

        assertEquals(200, response.getCode());
        assertEquals("accessToken", response.getBody().getAccesstoken());

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("Login Fails When Authentication Fails")
    void loginShouldFail_whenAuthenticationFails() {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Authentication failed"));

        ApiResponse<AuthResponse> response = authService.login(loginRequest);

        assertEquals("Authentication failed", response.getMessage());
        assertEquals(500, response.getCode());

        verify(authenticationManager, times(1)).authenticate(any());
    }
}