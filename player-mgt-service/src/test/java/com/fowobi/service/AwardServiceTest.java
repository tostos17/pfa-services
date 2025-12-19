package com.fowobi.service;

import com.fowobi.dto.AwardIssuanceRequest;
import com.fowobi.model.Award;
import com.fowobi.repository.AwardRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {

    @Mock
    AwardRepository awardRepository;

    @InjectMocks
    AwardService awardService;

    private Award award;
    private AwardIssuanceRequest awardIssuanceRequest;

    @BeforeEach
    void setUp() {
        award = new Award();
        awardIssuanceRequest = new AwardIssuanceRequest("Title", "issuer", "desc", LocalDate.now());
    }

    @Test
    @DisplayName("Add Award Success")
    void addAwardSuccess() {
        when(awardRepository.save(any())).thenReturn(award);

        String s = awardService.addAward(awardIssuanceRequest);

        assertEquals("Saved successfully", s);
        verify(awardRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Add Award Failure")
    void addAwardFailure() {
        when(awardRepository.save(any())).thenThrow(new RuntimeException("Unable to add new award"));

        String s = awardService.addAward(awardIssuanceRequest);

        assertEquals("Unable to add new award", s);
        verify(awardRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test Get All Awards")
    void getAllAwards() {
        when(awardRepository.findAll()).thenReturn(List.of(award));

        assertNotNull(awardService.getAllAwards());
    }

    @Test
    @DisplayName("Test Get Award By ID")
    void getAwardById() {
        when(awardRepository.findById(anyLong())).thenReturn(Optional.of(award));

        assertNotNull(awardService.getById(1));
    }
}