package com.fowobi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fowobi.api.ApiResponse;
import com.fowobi.dto.MatchDto;
import com.fowobi.dto.MatchRequestDto;
import com.fowobi.model.Match;
import com.fowobi.repository.MatchRepository;
import com.fowobi.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;

    @Value("${home.ground}")
    private String homeGround;

    @Override
    public ApiResponse<String> addMatch(MatchRequestDto matchRequestDto) {

        try {
            Match match = new Match();
            match.setHomeMatch(matchRequestDto.isHomeMatch());
            match.setOpponent(matchRequestDto.getOpponent());
            match.setMatchDate(matchRequestDto.getMatchDay());

            if(matchRequestDto.getKickOff() != null)
                match.setTime(matchRequestDto.getKickOff());

            if(matchRequestDto.isHomeMatch())
                match.setVenue(homeGround);
            else
                match.setVenue(matchRequestDto.getMatchVenue());

            matchRepository.save(match);
            return ApiResponse.generic(null, "Match saved successfully", true, 201);
        } catch (Exception e) {
            log.info("{}", e);
            return ApiResponse.error(null, "Failed to save match");
        }
    }

    @Override
    public ApiResponse<Page<MatchDto>> fetchAll(Pageable pageable) {

        try {
            Page<MatchDto> all = matchRepository.findAll(pageable)
                    .map(m -> new MatchDto(
                            m.getId(),
                            m.getOpponent(),
                            m.getVenue(),
                            m.isHomeMatch(),
                            m.isTerminated(),
                            m.getMatchDate(),
                            m.getTime(),
                            m.getYellowCards(),
                            m.getRedCards(),
                            m.getPenaltyConceded(),
                            m.getPenaltyGoalConceded(),
                            m.getPenaltyWon(),
                            m.getPenaltyGaolScored(),
                            m.getCornerConceded(),
                            m.getCornerWon(),
                            m.getSubstitutions(),
                            m.getLineup()
                    ));

            return ApiResponse.ok(all);
        } catch (Exception e) {
            log.info("Failed to fetch matches: {}", e);
            return ApiResponse.error(null, "Failed to fetch matches");
        }
    }

    @Override
    public ApiResponse<Page<MatchDto>> getAllResults(Pageable pageable) {

        try {
            Page<MatchDto> allTerminated = matchRepository.findAll(pageable)
                    .map(m -> {
                        if (m.isTerminated()) { // filter only terminated matches
                            return new MatchDto(
                                    m.getId(),
                                    m.getOpponent(),
                                    m.getVenue(),
                                    m.isHomeMatch(),
                                    true,
                                    m.getMatchDate(),
                                    m.getTime(),
                                    m.getYellowCards(),
                                    m.getRedCards(),
                                    m.getPenaltyConceded(),
                                    m.getPenaltyGoalConceded(),
                                    m.getPenaltyWon(),
                                    m.getPenaltyGaolScored(),
                                    m.getCornerConceded(),
                                    m.getCornerWon(),
                                    m.getSubstitutions(),
                                    m.getLineup()
                            );
                        }
                        return null; // will filter out later
                    });

            return ApiResponse.ok(allTerminated);
        } catch (Exception e) {
            log.info("Failed to fetch results: {}", e);
            return ApiResponse.error(null, "Failed to fetch results: {}");
        }
    }
}
