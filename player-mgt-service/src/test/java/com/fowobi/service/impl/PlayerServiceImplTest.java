package com.fowobi.service.impl;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.PlayerRegRequest;
import com.fowobi.model.Player;
import com.fowobi.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.util.ReflectionTestUtils.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Player Service Tests")
class PlayerServiceImplTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerServiceImpl playerService;

    @Test
    @DisplayName("Player Registration Success")
    void playerRegistrationSuccess() throws IOException {
        PlayerRegRequest playerRegRequest = new PlayerRegRequest();
        playerRegRequest.setHasHealthConcern("");

        setField(playerService,"uploadDir", "player-mgt-service/testdir");

        ApiResponse<String> stringApiResponse = playerService.registerPlayer(playerRegRequest);

        assertEquals(200, stringApiResponse.getCode());
        assertEquals("registered successfully", stringApiResponse.getBody());
    }

    @Test
    @DisplayName("Player Registration Failure")
    void playerRegistrationFailure() throws IOException {
        PlayerRegRequest playerRegRequest = new PlayerRegRequest();
        playerRegRequest.setHasHealthConcern("");

        when(playerRepository.save(any())).thenThrow(new RuntimeException());

        setField(playerService,"uploadDir", "player-mgt-service/testdir");

        ApiResponse<String> stringApiResponse = playerService.registerPlayer(playerRegRequest);

        assertEquals(500, stringApiResponse.getCode());
        assertEquals("register operation failed", stringApiResponse.getMessage());
    }

}