package com.fowobi.model;

import com.fowobi.constatnt.CompetitionStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tbl_matches")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String opponent;
    private String venue;
    private boolean homeMatch;
    private boolean terminated;
    private boolean started;
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
    private int halfDuration;

    @OneToMany
    private List<Substitution> substitutions;

    @OneToMany
    @JoinTable(name= "tbl_match_lineup")
    private List<Player> lineup;

    @OneToOne
    private PenaltyShootOut penaltyShootOut;


}
