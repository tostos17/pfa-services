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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
    @DisplayName("Player Registration Success With Photo")
    void playerRegistrationSuccessWithPhoto() throws IOException {
        PlayerRegRequest playerRegRequest = new PlayerRegRequest();
        playerRegRequest.setHasHealthConcern("");
        MultipartFile file = new MockMultipartFile(
                "file",                 // form field name
                "test.txt",             // original filename
                "text/plain",           // content type
                "Hello World".getBytes()// content
        );
        playerRegRequest.setPhoto(file);

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

    @Test
    @DisplayName("Update Player Data Success")
    void updatePlayer_success() {
        Player player = new Player();

        assertEquals("update successful", playerService.updatePlayer(player));
    }

    @Test
    @DisplayName("Update Player Data Failure")
    void updatePlayer_failure() {
        Player player = new Player();

        when(playerService.updatePlayer(any(Player.class))).thenThrow(new RuntimeException("Error occurred"));

        String s = playerService.updatePlayer(player);

        assertEquals("update operation failed", s);

    }

}