package com.fowobi.service.impl;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.PlayerPhotoUpdateRequest;
import com.fowobi.model.Player;
import com.fowobi.repository.PlayerRepository;
import com.fowobi.service.PlayerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerPhotoUploadTest {

    @Mock
    private PlayerRepository repository;

    @InjectMocks
    private PlayerServiceImpl service;

    private final String uploadDir = "target/test-uploads";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "uploadDir", uploadDir);
    }

    @AfterEach
    void cleanup() throws IOException {
        Path testDir = Paths.get(uploadDir);

        // Only attempt to clean up if the directory exists
        if (Files.exists(testDir)) {
            Files.walk(testDir)
                    .sorted(Comparator.reverseOrder())  // Delete files first, then directories
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }


    @Test
    void uploadPlayerPhoto_success() throws IOException {
        // Arrange
        Player player = new Player();
        player.setPlayerId("1L");

        MultipartFile photo = new MockMultipartFile(
                "photo",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
        request.setPlayerId("1L");
        request.setPhoto(photo);

        when(repository.findByPlayerId("1L")).thenReturn(Optional.of(player));
        when(repository.save(any(Player.class))).thenReturn(player);

        // Act
        ApiResponse<String> response = service.uploadPlayerPhoto(request);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Photo saved successfully", response.getBody());
        assertNotNull(player.getPassportPhotoUrl());
        assertTrue(player.getPassportPhotoUrl().startsWith("/uploads/"));

        verify(repository).save(player);
    }

    @Test
    void uploadPlayerPhoto_playerNotFound() throws IOException {
        // Arrange
        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
        request.setPlayerId("99L");

        when(repository.findByPlayerId("99L")).thenReturn(Optional.empty());

        // Act
        ApiResponse<String> response = service.uploadPlayerPhoto(request);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Not found", response.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void uploadPlayerPhoto_noPhotoProvided() throws IOException {
        // Arrange
        Player player = new Player();
        player.setPlayerId("1L");

        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
        request.setPlayerId("1L");
        request.setPhoto(null);

        when(repository.findByPlayerId("1L")).thenReturn(Optional.of(player));
        when(repository.save(any(Player.class))).thenReturn(player);

        // Act
        ApiResponse<String> response = service.uploadPlayerPhoto(request);

        // Assert
        assertTrue(response.isSuccess());
        assertNull(player.getPassportPhotoUrl());

        verify(repository).save(player);
    }

    @Test
    void uploadPlayerPhoto_fileWriteFails() throws IOException {
        // Arrange
        Player player = new Player();
        player.setPlayerId("1L");

        MultipartFile photo = mock(MultipartFile.class);
        when(photo.getOriginalFilename()).thenReturn("test.jpg");
        when(photo.getBytes()).thenThrow(new IOException("Disk error"));

        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
        request.setPlayerId("1L");
        request.setPhoto(photo);

        when(repository.findByPlayerId("1L")).thenReturn(Optional.of(player));

        // Act
        ApiResponse<String> response = service.uploadPlayerPhoto(request);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Disk error", response.getMessage());

        verify(repository, never()).save(any());
    }

}

