package com.fowobi.dto;

import com.fowobi.model.Player;
import com.fowobi.model.Substitution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDto {
    private long id;
    private String opponent;
    private String venue;
    private boolean homeMatch;
    private boolean isTerminated;
    private LocalDate matchDate;
    private LocalTime time;
    private int yellowCards;
    private int redCards;
    private int penaltyConceded;
    private int penaltyGoalConceded;
    private int penaltyWon;
    private int penaltyGaolScored;
    private int cornerConceded;
    private int cornerWon;
    private List<Substitution> substitutions;
    private List<Player> players;
}
