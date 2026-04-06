package com.fowobi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fowobi.api.ApiResponse;
import com.fowobi.dto.MatchDto;
import com.fowobi.dto.MatchDurationUpdateRequest;
import com.fowobi.dto.MatchRequestDto;
import com.fowobi.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MatchController Tests")
class MatchControllerTest {

    // ── adjust to match com.fowobi.utils.AppConstants.BASE_CONTEXT ──────────
    private static final String BASE       = "/pfa";
    private static final String MATCH_BASE = BASE + "/match";

    @Mock  private MatchService      matchService;
    @InjectMocks private MatchController matchController;

    private MockMvc      mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(matchController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    // =========================================================================
    // POST /match  –  saveMatch
    // =========================================================================

    @Test
    @DisplayName("POST /match – HTTP 200 and service called once")
    void saveMatch_http200AndServiceInvoked() throws Exception {
        // ApiResponse<String> serialises fine – no PageImpl involved.
        when(matchService.addMatch(any(MatchRequestDto.class)))
                .thenReturn(ApiResponse.ok("Match saved"));

        mockMvc.perform(post(MATCH_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildMatchRequestDto2())))
                .andExpect(status().isOk());

        verify(matchService, times(1)).addMatch(any(MatchRequestDto.class));
    }

    @Test
    @DisplayName("saveMatch – delegates DTO to service and returns its response")
    void saveMatch_delegatesAndReturns() {
        MatchRequestDto     dto      = buildMatchRequestDto();
        ApiResponse<String> expected = ApiResponse.ok("ok");
        when(matchService.addMatch(dto)).thenReturn(expected);

        ApiResponse<String> result = matchController.saveMatch(dto);

        verify(matchService).addMatch(dto);
        assertThat(result).isSameAs(expected);
    }

    @Test
    @DisplayName("saveMatch – propagates whatever the service returns")
    void saveMatch_propagatesServiceResponse() {
        ApiResponse<String> serviceResponse = ApiResponse.ok("Saved");
        when(matchService.addMatch(any())).thenReturn(serviceResponse);

        assertThat(matchController.saveMatch(buildMatchRequestDto())).isSameAs(serviceResponse);
    }

    // =========================================================================
    // GET /match  –  getAllMatches
    // =========================================================================

    /**
     * MockMvc HTTP test – service returns {@code null} so Jackson never attempts
     * to serialise {@code PageImpl} and the response body is simply empty/null.
     * We only care that the endpoint routes correctly and returns 200.
     */
    @Test
    @DisplayName("GET /match – HTTP 200 and service called once")
    void getAllMatches_http200AndServiceInvoked() throws Exception {
        when(matchService.fetchAll(any(Pageable.class))).thenReturn(null);

        mockMvc.perform(get(MATCH_BASE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(matchService, times(1)).fetchAll(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /match – custom page params are forwarded to service")
    void getAllMatches_customPageableForwarded() throws Exception {
        when(matchService.fetchAll(any(Pageable.class))).thenReturn(null);

        mockMvc.perform(get(MATCH_BASE)
                        .param("page", "2")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(matchService).fetchAll(any(Pageable.class));
    }

    @Test
    @DisplayName("getAllMatches – delegates Pageable to service and returns its response")
    void getAllMatches_delegatesAndReturns() {
        Pageable                    pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "matchDate"));
        Page<MatchDto>              page     = new PageImpl<>(Collections.emptyList());
        ApiResponse<Page<MatchDto>> expected = ApiResponse.ok(page);
        when(matchService.fetchAll(pageable)).thenReturn(expected);

        ApiResponse<Page<MatchDto>> result = matchController.getAllMatches(pageable);

        verify(matchService).fetchAll(pageable);
        assertThat(result).isSameAs(expected);
    }

    @Test
    @DisplayName("getAllMatches – propagates whatever the service returns")
    void getAllMatches_propagatesServiceResponse() {
        ApiResponse<Page<MatchDto>> serviceResponse =
                ApiResponse.ok(new PageImpl<>(Collections.emptyList()));
        when(matchService.fetchAll(any())).thenReturn(serviceResponse);

        assertThat(matchController.getAllMatches(Pageable.unpaged())).isSameAs(serviceResponse);
    }

    // =========================================================================
    // GET /match/results  –  getAllResults
    // =========================================================================

    @Test
    @DisplayName("GET /match/results – HTTP 200 and service called once")
    void getAllResults_http200AndServiceInvoked() throws Exception {
        when(matchService.getAllResults(any(Pageable.class))).thenReturn(null);

        mockMvc.perform(get(MATCH_BASE + "/results").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(matchService, times(1)).getAllResults(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /match/results – custom page params are forwarded to service")
    void getAllResults_customPageableForwarded() throws Exception {
        when(matchService.getAllResults(any(Pageable.class))).thenReturn(null);

        mockMvc.perform(get(MATCH_BASE + "/results")
                        .param("page", "1")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(matchService).getAllResults(any(Pageable.class));
    }

    @Test
    @DisplayName("getAllResults – delegates Pageable to service and returns its response")
    void getAllResults_delegatesAndReturns() {
        Pageable                    pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "matchDate"));
        Page<MatchDto>              page     = new PageImpl<>(List.of(new MatchDto()));
        ApiResponse<Page<MatchDto>> expected = ApiResponse.ok(page);
        when(matchService.getAllResults(pageable)).thenReturn(expected);

        ApiResponse<Page<MatchDto>> result = matchController.getAllResults(pageable);

        verify(matchService).getAllResults(pageable);
        assertThat(result).isSameAs(expected);
    }

    @Test
    @DisplayName("getAllResults – propagates whatever the service returns")
    void getAllResults_propagatesServiceResponse() {
        ApiResponse<Page<MatchDto>> serviceResponse =
                ApiResponse.ok(new PageImpl<>(Collections.emptyList()));
        when(matchService.getAllResults(any())).thenReturn(serviceResponse);

        assertThat(matchController.getAllResults(Pageable.unpaged())).isSameAs(serviceResponse);
    }

    // =========================================================================
    // POST /match/setmatchduration  –  setMatchDuration
    // =========================================================================

    @Test
    @DisplayName("POST /match/setmatchduration – HTTP 200 and service called once")
    void setMatchDuration_http200AndServiceInvoked() throws Exception {
        when(matchService.setMatchDuration(any(MatchDurationUpdateRequest.class)))
                .thenReturn(ApiResponse.ok("Duration updated"));

        mockMvc.perform(post(MATCH_BASE + "/setmatchduration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildDurationRequest(45))))
                .andExpect(status().isOk());

        verify(matchService, times(1)).setMatchDuration(any(MatchDurationUpdateRequest.class));
    }

    @Test
    @DisplayName("setMatchDuration – delegates request to service and returns its response")
    void setMatchDuration_delegatesAndReturns() {
        MatchDurationUpdateRequest request  = buildDurationRequest(45);
        ApiResponse<String>        expected = ApiResponse.ok("updated");
        when(matchService.setMatchDuration(request)).thenReturn(expected);

        ApiResponse<String> result = matchController.setMatchDuration(request);

        verify(matchService).setMatchDuration(request);
        assertThat(result).isSameAs(expected);
    }

    @Test
    @DisplayName("setMatchDuration – propagates whatever the service returns")
    void setMatchDuration_propagatesServiceResponse() {
        ApiResponse<String> serviceResponse = ApiResponse.ok("Done");
        when(matchService.setMatchDuration(any())).thenReturn(serviceResponse);

        assertThat(matchController.setMatchDuration(buildDurationRequest(90)))
                .isSameAs(serviceResponse);
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    /** Adjust field values to satisfy any @NotNull / @NotBlank on MatchRequestDto. */
    private MatchRequestDto buildMatchRequestDto() {
        return new MatchRequestDto();
        // e.g.: dto.setHomeTeamId(1L); dto.setAwayTeamId(2L);
    }

    private MatchRequestDto buildMatchRequestDto2() {
        MatchRequestDto dto = new MatchRequestDto();
        dto.setMatchDay(LocalDate.of(2026, 1, 1));
        // e.g.: dto.setHomeTeamId(1L); dto.setAwayTeamId(2L);

        return dto;
    }

    /** Adjust field values to satisfy any @NotNull / @Min on MatchDurationUpdateRequest. */
    private MatchDurationUpdateRequest buildDurationRequest(int minutes) {
        MatchDurationUpdateRequest request = new MatchDurationUpdateRequest();
        request.setHalfDuration(35);
        request.setMatchId(1L);

        return request;
    }
}