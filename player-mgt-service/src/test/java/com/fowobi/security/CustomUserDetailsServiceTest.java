package com.fowobi.security;

import com.fowobi.common.dto.AppUser;
import com.fowobi.repository.AppUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Custom User Details Service Test")
class CustomUserDetailsServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @InjectMocks
    CustomUserDetailsService service;

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowUserNotFoundException() {
        String username = "user";

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername(username)
        );

        assertEquals("User not found", ex.getMessage());

        verify(appUserRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Load User Success")
    void loadUserSuccess() {
        String username = "user";

        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(AppUser.builder()
                .username("user")
                .password("pass")
                .roles(new HashSet<>())
                .build()));

        UserDetails userDetails = service.loadUserByUsername(username);

        assertEquals("user", userDetails.getUsername());
        assertEquals("pass", userDetails.getPassword());

    }
}