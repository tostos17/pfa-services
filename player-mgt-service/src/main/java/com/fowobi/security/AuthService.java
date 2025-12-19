package com.fowobi.security;

import com.fowobi.api.ApiResponse;
import com.fowobi.common.dto.AppUser;
import com.fowobi.common.dto.AuthResponse;
import com.fowobi.common.dto.LoginRequest;
import com.fowobi.common.dto.RegisterRequest;
import com.fowobi.repository.AppUserRepository;
import com.fowobi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtil;

    public AuthService(AppUserRepository repo, PasswordEncoder encoder,
                       AuthenticationManager authManager, JwtUtils jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {
        if (repo.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));
        user.setUserId(UUID.randomUUID().toString());
        user.getRoles().add("ROLE_USER");

        log.info("User to save: {}", user);

        try {
            repo.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ApiResponse<AuthResponse> login(LoginRequest request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(), request.password())
            );
        } catch (Exception e) {
            return ApiResponse.error(null, e.getMessage());
        }

        String token = jwtUtil.generateToken(request.username());
        AuthResponse response = new AuthResponse(token, request.username());
        log.info("Response here: {}", response);
        return ApiResponse.ok(response);
    }
}

