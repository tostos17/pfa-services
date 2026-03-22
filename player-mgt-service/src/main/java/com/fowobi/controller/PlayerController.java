package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.*;
import com.fowobi.model.Player;
import com.fowobi.service.AwardService;
import com.fowobi.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.fowobi.constatnt.AppConstants.PAGE_SIZE;
import static com.fowobi.utils.AppConstants.BASE_CONTEXT;

@Slf4j
@RestController
@RequestMapping(BASE_CONTEXT + "/player")
@Tag(name = "Player Controller", description = "Manages player registration, data update and retrieval")
public class PlayerController {

    private final PlayerService playerService;
    private final AwardService awardService;

    public PlayerController(PlayerService playerService, AwardService awardService) {
        this.playerService = playerService;
        this.awardService = awardService;
    }

    @Operation(summary = "Register one new player", description = "Validates input data and processes player registration")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> registerPlayer(
            @Parameter(description = "Player request object")
            @ModelAttribute
            @Valid
            PlayerRegRequest request) throws IOException {
        return playerService.registerPlayer(request);
    }

    @Operation(summary = "Get all players", description = "Retrieves a list of all players. Result is paginated")
    @GetMapping
    public ApiResponse<Page<Player>> getPlayers(
            @PageableDefault(size = PAGE_SIZE) Pageable pageable
    ) {
        return playerService.getAll(pageable);
    }

    @Operation(summary = "Get summary", description = "Provides summarized player data. Result is paginated")
    @GetMapping("/getsummary")
    public ApiResponse<Page<FetchPlayerResponse>> fetchPlayers(
            @Parameter(description = "Optional params including page, size, and sort") @PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        return playerService.getPlayerSummaries(pageable);
    }

    @Operation(summary = "Get player details", description = "Retrieves single player. Requires id parameter")
    @GetMapping("/getplayerdetails/{id}")
    public ApiResponse<PlayerDto> getPlayerById(@Parameter(description = "Player ID") @PathVariable String id) {
        return playerService.findByPlayerId(id);
    }

    @Operation(summary = "Find by registration date", description = "Returns a list of players who registered within a given date range")
    @GetMapping("/getplayersbyregdate")
    public ApiResponse<Page<FetchPlayerResponse>> getPlayersByRegDate(
            @Parameter(description = "Start date for filter") @RequestParam LocalDate fromDate,
            @Parameter(description = "End date for filter") @RequestParam LocalDate toDate,
            @Parameter(description = "Optional page details") @PageableDefault(size = PAGE_SIZE) Pageable pageable
            ) {
        return playerService.getPlayersByRegDate(fromDate, toDate, pageable);
    }

    @Operation(summary = "Find by registration date", description = "Returns a list of players who registered within a given date range")
    @GetMapping("/getcategory/{category}")
    public ApiResponse<Page<FetchPlayerResponse>> getCategory(
            @Parameter(description = "Category of players. Example: U13") @PathVariable(name = "category") String category,
            @Parameter(description = "Optional page details") @PageableDefault(size = PAGE_SIZE) Pageable pageable
    ) {
        return playerService.findByCategory(category, pageable);
    }

    @Operation(summary = "Find by exact age", description = "Returns a list of players who have an exact age")
    @GetMapping("/getplayersbyexactage/{age}")
    public ApiResponse<Page<FetchPlayerResponse>> findByExactAge(
            @Parameter(description = "age") @PathVariable(name = "age") int age,
            @Parameter(description = "Optional page details") @PageableDefault(size = PAGE_SIZE) Pageable pageable
    ) {
        return playerService.findByExactAge(age, pageable);
    }

    @Operation(summary = "Find by minimum age", description = "Returns a list of players who are not less than the given age")
    @GetMapping("/getplayersbyminage/{age}")
    public ApiResponse<Page<FetchPlayerResponse>> findByMinAge(
            @Parameter(description = "age") @PathVariable(name = "age") int age,
            @Parameter(description = "Optional page details") @PageableDefault(size = PAGE_SIZE) Pageable pageable
    ) {
        return playerService.findByMinAge(age, pageable);
    }

    @Operation(summary = "Find by maximum age", description = "Returns a list of players who are not older than the given age")
    @GetMapping("/getplayersbymaxage/{age}")
    public ApiResponse<Page<FetchPlayerResponse>> findByMaxAge(
            @Parameter(description = "age") @PathVariable(name = "age") int age,
            @Parameter(description = "Optional page details") @PageableDefault(size = PAGE_SIZE) Pageable pageable
    ) {
        return playerService.findByMaxAge(age, pageable);
    }

    @Operation(summary = "Upload player photo", description = "Upload display photo for a player")
    @PostMapping(value = "/uploadphoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadPlayerPhoto(@Parameter(description = "Player photo update request object") @ModelAttribute @Valid PlayerPhotoUpdateRequest request) throws IOException {
        log.info("Photo upload request received");
        return playerService.uploadPlayerPhoto(request);
    }

    @Operation(summary = "Update player category")
    @PostMapping("/updateCategory")
    public ApiResponse<String> modifyPlayerCategory(@Parameter(description = "Category update request object") @RequestBody CategoryUpdateRequest request) {
        return playerService.modifyPlayerCategory(request);
    }

//    @PostMapping("/award")
//    public ApiResponse<String> issueAward(AwardIssuanceRequest request) {
//        return ApiResponse.ok(awardService.addAward(request));
//    }
}
