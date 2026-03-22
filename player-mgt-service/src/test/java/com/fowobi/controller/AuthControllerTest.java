package com.fowobi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fowobi.api.ApiResponse;
import com.fowobi.common.dto.AuthResponse;
import com.fowobi.common.dto.LoginRequest;
import com.fowobi.common.dto.RegisterRequest;
import com.fowobi.config.SecurityTestConfig;
import com.fowobi.security.AuthService;
import com.fowobi.security.CustomUserDetailsService;
import com.fowobi.security.JwtAuthFilter;
import com.fowobi.utils.JwtUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Auth Controller Test")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

//    @MockBean
//    JwtUtils jwtUtils;

//    @MockBean
//    JwtAuthFilter jwtAuthFilter;

    @Test
    @DisplayName("Register app user success")
    void registerAppUser_success() throws Exception {
        RegisterRequest request = new RegisterRequest("john", "password", "role");

        when(authService.register(any())).thenReturn(ApiResponse.ok("Successfully Registered"));

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value(Matchers.containsString("Successfully Registered")));
    }

    @Test
    @DisplayName("Register app user should fail when username is blank")
    void registerAppUser_shouldFailWhenUsernameIsBlank() throws Exception {
        RegisterRequest request = new RegisterRequest("", "password", "role");

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
                "http://localhost:8080/pfa/api/auth/register"
        );

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(Matchers.contains("username is required")));
    }

    @Test
    @DisplayName("Register app user should fail when password is blank")
    void registerAppUser_shouldFailWhenPasswordIsBlank() throws Exception {
        RegisterRequest request = new RegisterRequest("user1", "", "role");

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
                "http://localhost:8080/pfa/api/auth/register"
        );

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isBadRequest())
//                .andExpect(content().string(request.username() + " created successfully"))
                .andExpect(jsonPath("$[0]").value(Matchers.containsString("password")));
    }

    @Test
    @DisplayName("Register app user should fail when role is blank")
    void registerAppUser_shouldFailWhenRoleIsBlank() throws Exception {
        RegisterRequest request = new RegisterRequest("user1", "password", "");

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
                "http://localhost:8080/pfa/api/auth/register"
        );

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isBadRequest())
//                .andExpect(content().string(request.username() + " created successfully"))
                .andExpect(jsonPath("$").value(Matchers.contains("enter a valid role")));
    }

    @Test
    @DisplayName("Login should fail when password is blank")
    void loginShouldFailWhenPasswordIsBlank() throws Exception {
        LoginRequest request = new LoginRequest("user1", "");

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isBadRequest())
//                .andExpect(content().string(request.username() + " created successfully"))
                .andExpect(jsonPath("$").value(Matchers.contains("password is required")));
    }

    @Test
    @DisplayName("Login should fail when username is blank")
    void loginShouldFailWhenUsernameIsBlank() throws Exception {
        LoginRequest request = new LoginRequest("", "password");

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isBadRequest())
//                .andExpect(content().string(request.username() + " created successfully"))
                .andExpect(jsonPath("$").value(Matchers.contains("username is required")));
    }

    @Test
    @DisplayName("Login success")
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest("user", "password");
        AuthResponse response = new AuthResponse("access token", "user");

        when(authService.login(any())).thenReturn(ApiResponse.ok(response));

        // Use MockMvcRequestBuilders to create a request builder
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/pfa/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))  // Convert request object to JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.name").value(Matchers.containsString("user")));
    }

}