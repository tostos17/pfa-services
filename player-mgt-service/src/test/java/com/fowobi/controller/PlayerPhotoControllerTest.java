package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.config.SecurityTestConfig;
import com.fowobi.dto.PlayerPhotoUpdateRequest;
import com.fowobi.repository.PlayerRepository;
import com.fowobi.security.CustomUserDetailsService;
import com.fowobi.service.AwardService;
import com.fowobi.service.PlayerService;
import com.fowobi.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlayerController.class)
@Import(SecurityTestConfig.class)
class PlayerPhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private AwardService awardService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    void uploadPlayerPhoto_success() throws Exception {
        // Arrange
        MultipartFile photo = new MockMultipartFile(
                "photo",                    // field name
                "test.jpg",                  // file name
                "image/jpeg",                // content type
                "fake-image-content".getBytes()  // content
        );

        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
        request.setPlayerId("1L");
        request.setPhoto(photo);

        ApiResponse<String> response = ApiResponse.ok("Photo saved successfully");

        // Mock the service call
        when(playerService.uploadPlayerPhoto(any(PlayerPhotoUpdateRequest.class)))
                .thenReturn(response);

        String csrfToken = "your-csrf-token";

        // Act & Assert
        mockMvc.perform(multipart("/pfa/player/uploadphoto")
                        .file("photo", photo.getBytes())  // Attach the file
                        .param("playerId", "1")           // Add other form parameters
                        .header("X-CSRF-TOKEN", csrfToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("Photo saved successfully"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    void uploadPlayerPhoto_playerNotFound() throws Exception {
        // Arrange
        MultipartFile photo = new MockMultipartFile(
                "photo",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
        request.setPlayerId("99L");
        request.setPhoto(photo);

        ApiResponse<String> response = ApiResponse.error(null, "Not found");

        // Mock the service call
        when(playerService.uploadPlayerPhoto(any(PlayerPhotoUpdateRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(multipart("/pfa/player/uploadphoto")
                        .file("photo", photo.getBytes())
                        .param("playerId", "99")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("Not found"));
    }

//    @Test
//    @WithMockUser(username = "user1", roles = {"ADMIN"})
//    void uploadPlayerPhoto_ioException() throws Exception {
//        // Arrange
//        MultipartFile photo = new MockMultipartFile(
//                "photo",
//                "test.jpg",
//                "image/jpeg",
//                "fake-image-content".getBytes()
//        );
//
//        PlayerPhotoUpdateRequest request = new PlayerPhotoUpdateRequest();
//        request.setPlayerId("1L");
//        request.setPhoto(photo);
//
//        ApiResponse<String> response = ApiResponse.error(null, "Disk error");
//
//        when(playerRepository.findByPlayerId(anyString()))
//                .thenThrow(new IOException("Disk error"));
//
//        // Act & Assert
//        mockMvc.perform(multipart("/pfa/player/uploadphoto")
//                        .file("photo", photo.getBytes())
//                        .param("playerId", "1")
//                        .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(status().isInternalServerError())  // 500 for internal server errors
//                .andExpect(jsonPath("$.message").value("Disk error"));
//    }
}

