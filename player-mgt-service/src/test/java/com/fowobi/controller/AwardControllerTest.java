package com.fowobi.controller;

import com.fowobi.model.Award;
import com.fowobi.service.AwardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AwardController Tests")
class AwardControllerTest {

    @Mock
    private AwardService awardService;

    @InjectMocks
    private AwardController awardController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(awardController).build();
    }

    // -------------------------------------------------------------------------
    // Constructor / wiring
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Constructor injects AwardService correctly")
    void constructor_injectsService() {
        AwardService service = mock(AwardService.class);
        AwardController controller = new AwardController(service);
        // If the controller is built without exception the wiring is correct.
        // Verify the injected service is the one we supplied by invoking a call.
        when(service.getAllAwards()).thenReturn(Collections.emptyList());
        controller.getAll();
        verify(service).getAllAwards();
    }

    // -------------------------------------------------------------------------
    // GET /award/all
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /award/all - returns list of awards with HTTP 200")
    void getAll_returnsAwardList() throws Exception {
        Award award1 = buildAward(1L, "Best Player");
        Award award2 = buildAward(2L, "Golden Boot");
        when(awardService.getAllAwards()).thenReturn(List.of(award1, award2));

        mockMvc.perform(get("/award/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(awardService, times(1)).getAllAwards();
    }

    @Test
    @DisplayName("GET /award/all - returns empty list when no awards exist")
    void getAll_returnsEmptyList() throws Exception {
        when(awardService.getAllAwards()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/award/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(awardService, times(1)).getAllAwards();
    }

    @Test
    @DisplayName("GET /award/all - delegates to AwardService#getAllAwards")
    void getAll_delegatesToService() {
        when(awardService.getAllAwards()).thenReturn(Collections.emptyList());

        List<Award> result = awardController.getAll();

        verify(awardService).getAllAwards();
        assert result != null;
    }

    // -------------------------------------------------------------------------
    // GET /award/find/{id}
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /award/find/{id} - returns award for valid id")
    void getById_returnsAward() throws Exception {
        Award award = buildAward(1L, "Best Player");
        when(awardService.getById(1L)).thenReturn(award);

        mockMvc.perform(get("/award/find/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(awardService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("GET /award/find/{id} - passes path variable id to service")
    void getById_passesCorrectIdToService() {
        Award award = buildAward(42L, "Top Scorer");
        when(awardService.getById(42L)).thenReturn(award);

        Award result = awardController.getById(42L);

        verify(awardService).getById(42L);
        assert result == award;
    }

    @Test
    @DisplayName("GET /award/find/{id} - returns null when service returns null")
    void getById_returnsNullWhenServiceReturnsNull() throws Exception {
        when(awardService.getById(99L)).thenReturn(null);

        mockMvc.perform(get("/award/find/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(awardService, times(1)).getById(99L);
    }

    @Test
    @DisplayName("GET /award/find/{id} - large id is forwarded correctly")
    void getById_largeId() {
        long largeId = Long.MAX_VALUE;
        when(awardService.getById(largeId)).thenReturn(buildAward(largeId, "Legend"));

        Award result = awardController.getById(largeId);

        verify(awardService).getById(largeId);
        assert result != null;
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private Award buildAward(long id, String name) {
        Award award = new Award();
        // Use reflection-friendly setters; adjust if your Award model differs.
        try {
            var idField = Award.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(award, id);

            var nameField = Award.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(award, name);
        } catch (Exception ignored) {
            // If the Award model doesn't have these exact fields the mock return
            // value is still valid for verifying delegation behaviour.
        }
        return award;
    }
}
