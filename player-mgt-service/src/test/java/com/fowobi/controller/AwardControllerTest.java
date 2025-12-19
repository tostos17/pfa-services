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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Award Controller Tests")
class AwardControllerTest {

    @Mock
    AwardService awardService;

    @InjectMocks
    AwardController awardController;

    private Award award;


    @BeforeEach
    void setUp() {
        award = new Award();
        award.setDate(LocalDate.now());
        award.setDescription("Special baller award");
        award.setDateReceived(LocalDate.now());
        award.setIssuer("Ballers Magazine");
        award.setTitle("MVP");
    }

    @Test
    @DisplayName("Fetch All Success")
    void getAllSuccess() {
        when(awardService.getAllAwards()).thenReturn(List.of(award));

        List<Award> allAwards = awardController.getAll();

        assertEquals(1, allAwards.size());

        verify(awardService, times(1)).getAllAwards();
    }

    @Test
    @DisplayName("Fetch Award By ID Success")
    void getAwardByIdSuccess() {
        when(awardService.getById(anyLong())).thenReturn(award);

        Award awardById = awardController.getById(1);

        assertNotNull(awardById);

        verify(awardService, times(1)).getById(anyLong());
    }
}