package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.AwardIssuanceRequest;
import com.fowobi.dto.FetchPlayerResponse;
import com.fowobi.dto.PlayerRegRequest;
import com.fowobi.model.Award;
import com.fowobi.model.Player;
import com.fowobi.service.AwardService;
import com.fowobi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Player Controller Tests")
class PlayerControllerTest {

    @Mock
    PlayerService playerService;

    @Mock
    AwardService awardService;

    @InjectMocks
    PlayerController playerController;

    private Award award;
    private PlayerRegRequest playerRegRequest;
    private Page<Player> players;
    private Page<FetchPlayerResponse> playerSummaryList;

    @BeforeEach
    void setUp() {
        award = new Award();
        award.setDate(LocalDate.now());
        award.setDescription("Special baller award");
        award.setDateReceived(LocalDate.now());
        award.setIssuer("Ballers Magazine");
        award.setTitle("MVP");

        playerRegRequest = new PlayerRegRequest();
        playerRegRequest.setPhoto(new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Hello World".getBytes()
        ));

        List<Player> playerList = List.of(new Player());
        PageRequest pageRequest = PageRequest.of(0, 10);
        players = new PageImpl<>(playerList, pageRequest, playerList.size());

        List<FetchPlayerResponse> summaryList = List.of(new FetchPlayerResponse());
        PageRequest playerSummaryRequest = PageRequest.of(0, 10);
        playerSummaryList = new PageImpl<>(summaryList, playerSummaryRequest, summaryList.size());
    }

    @Test
    @DisplayName("Register Player Success")
    void registerPlayerSuccess() throws IOException {
        when(playerService.registerPlayer(any())).thenReturn(ApiResponse.ok("Success"));

        ApiResponse<String> stringApiResponse = playerController.registerPlayer(playerRegRequest);

        assertEquals(200, stringApiResponse.getCode());
        verify(playerService, times(1)).registerPlayer(any());
    }

    @Test
    @DisplayName("Get All Players Success")
    void getAllPlayersSuccessfully() {
        when(playerService.getAll(anyInt(), anyInt())).thenReturn(ApiResponse.ok(players));

        ApiResponse<Page<Player>> players = playerController.getPlayers(0, 10);

        assertEquals(200, players.getCode());
        verify(playerService, times(1)).getAll(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Fetch Player Summary List Success")
    void fetchPlayerSummaryList() {
        when(playerService.getPlayerSummaries(any())).thenReturn(ApiResponse.ok(playerSummaryList));

        ApiResponse<Page<FetchPlayerResponse>> summaries = playerController.getPlayers(any(Pageable.class));

        assertEquals(200, summaries.getCode());
        verify(playerService, times(1)).getPlayerSummaries(any());
    }

    @Test
    @DisplayName("Issue Award Success")
    void issueAwardSuccess() {
        when(awardService.addAward(any())).thenReturn("Success");

        ApiResponse<String> stringApiResponse = playerController.issueAward(new AwardIssuanceRequest());

        assertEquals(200, stringApiResponse.getCode());
        verify(awardService, times(1)).addAward(any());
    }

}