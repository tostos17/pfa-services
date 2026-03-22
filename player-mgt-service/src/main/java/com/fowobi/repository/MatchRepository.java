package com.fowobi.repository;

import com.fowobi.dto.MatchDto;
import com.fowobi.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("""
        SELECT new com.fowobi.dto.MatchDto(
            m.id,
            m.opponent,
            m.venue,
            m.homeMatch,
            m.matchDate,
            m.time,
            m.yellowCards,
            m.redCards,
            m.penaltyConceded,
            m.penaltyGoalConceded,
            m.penaltyWon,
            m.penaltyGaolScored,
            m.cornerConceded,
            m.cornerWon,
            m.substitutions,
            m.lineup
        )
        FROM Match m
       """)
    Page<MatchDto> fetchAll(Pageable pageable);
}
