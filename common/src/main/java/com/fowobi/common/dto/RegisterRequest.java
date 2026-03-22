package com.fowobi.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "username is required")
        String username,

        @NotBlank(message = "password is required")
        @Size(min = 6, message = "password must contain at least six characters")
        String password,

        @NotBlank(message = "enter a valid role")
        String role
) {}


