package com.fowobi.service;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.*;
import com.fowobi.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface PlayerService {
    ApiResponse<String> registerPlayer(PlayerRegRequest request) throws IOException;

    String updatePlayer(Player player);

    ApiResponse<Page<Player>> getAll(Pageable pageable);

    ApiResponse<Page<FetchPlayerResponse>> getPlayerSummaries(Pageable pageable);

    ApiResponse<PlayerDto> findByPlayerId(String id);
    
    ApiResponse<Page<FetchPlayerResponse>> findByCategory(String category, Pageable pageable);

    ApiResponse<String> uploadPlayerPhoto(PlayerPhotoUpdateRequest request) throws IOException;

    ApiResponse<Page<FetchPlayerResponse>> getPlayersByRegDate(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    ApiResponse<Page<FetchPlayerResponse>> findByExactAge(int age, Pageable pageable);

    ApiResponse<Page<FetchPlayerResponse>> findByMinAge(int age, Pageable pageable);

    ApiResponse<Page<FetchPlayerResponse>> findByMaxAge(int age, Pageable pageable);

    ApiResponse<String> modifyPlayerCategory(CategoryUpdateRequest request);
}
