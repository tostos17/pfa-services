package com.fowobi.controller;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.MatchDto;
import com.fowobi.dto.MatchRequestDto;
import com.fowobi.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fowobi.constatnt.AppConstants.PAGE_SIZE;
import static com.fowobi.utils.AppConstants.BASE_CONTEXT;

@RestController
@RequestMapping(BASE_CONTEXT + "/match")
@RequiredArgsConstructor
@Validated
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    @Operation(summary = "Save match", description = "Save a new match")
    ApiResponse<String> saveMatch(@Parameter(description = "Match request dto object") @RequestBody MatchRequestDto requestDto) {
        return matchService.addMatch(requestDto);
    }

    @GetMapping
    @Operation(summary = "Get all matches", description = "Fetch all matches and returns paginated response")
    ApiResponse<Page<MatchDto>> getAllMatches(@Parameter(description = "Optional Pageable") @PageableDefault(sort = "matchDate", direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        return matchService.fetchAll(pageable);
    }

    @GetMapping("/results")
    @Operation(summary = "Get all results", description = "Fetch all terminated matches")
    ApiResponse<Page<MatchDto>> getAllResults(@PageableDefault(sort = "matchDate", direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        return matchService.getAllResults(pageable);
    }

}
