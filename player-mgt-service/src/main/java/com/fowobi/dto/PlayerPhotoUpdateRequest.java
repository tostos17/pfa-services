package com.fowobi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPhotoUpdateRequest {

    @NotNull( message = "Player ID is required")
    private String playerId;

    @NotNull( message = "Photo is required" )
    private MultipartFile photo;
}
