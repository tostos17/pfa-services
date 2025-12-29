package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.AwardIssuanceRequest;
import com.fowobi.dto.FetchPlayerResponse;
import com.fowobi.dto.PlayerDto;
import com.fowobi.dto.PlayerRegRequest;
import com.fowobi.model.Player;
import com.fowobi.service.AwardService;
import com.fowobi.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.fowobi.utils.AppConstants.BASE_CONTEXT;

@RestController
@RequestMapping(BASE_CONTEXT + "/player")
public class PlayerController {

    private final PlayerService playerService;
    private final AwardService awardService;

    public PlayerController(PlayerService playerService, AwardService awardService) {
        this.playerService = playerService;
        this.awardService = awardService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> registerPlayer(@ModelAttribute @Valid PlayerRegRequest request) throws IOException {
        return playerService.registerPlayer(request);
    }

    @GetMapping
    public ApiResponse<Page<Player>> getPlayers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return playerService.getAll(pageNumber, pageSize);
    }

    @GetMapping("/getsummary")
    public ApiResponse<Page<FetchPlayerResponse>> getPlayers(
            @PageableDefault(size = 10) Pageable pageable) {
        return playerService.getPlayerSummaries(pageable);
    }

    @GetMapping("/getplayerdetails/{id}")
    public ApiResponse<PlayerDto> getPlayerById(@PathVariable String id) {
        return playerService.findByPlayerId(id);
    }


    @PostMapping("/award")
    public ApiResponse<String> issueAward(AwardIssuanceRequest request) {
        return ApiResponse.ok(awardService.addAward(request));
    }
}
