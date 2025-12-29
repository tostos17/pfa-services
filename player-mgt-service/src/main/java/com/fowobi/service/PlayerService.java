package com.fowobi.service;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.AwardIssuanceRequest;
import com.fowobi.dto.FetchPlayerResponse;
import com.fowobi.dto.PlayerDto;
import com.fowobi.dto.PlayerRegRequest;
import com.fowobi.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface PlayerService {
    ApiResponse<String> registerPlayer(PlayerRegRequest request) throws IOException;

    String updatePlayer(Player player);

//    Player findById(long id);
//
//    List<Player> getAll();
//
//    List<Player> findByMaxAge(int age);
//
//    ResponseEntity<String> issueAward(AwardIssuanceRequest request);

    ApiResponse<Page<Player>> getAll(int pageNumber, int pageSize);

    ApiResponse<Page<FetchPlayerResponse>> getPlayerSummaries(Pageable pageable);

    ApiResponse<PlayerDto> findByPlayerId(String id);
}
