package com.fowobi.model;

import com.fowobi.constatnt.CompetitionStage;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tbl_matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String opponent;
    private String venue;
    private boolean homeMatch;
    private LocalDate date;
    private LocalTime time;
    private int yellowCards;
    private int redCards;
    private int penaltyConceded;
    private int penaltyGoalConceded;
    private int penaltyWon;
    private int penaltyGaolScored;
    private int cornerConceded;
    private int cornerWon;

    @Enumerated(EnumType.STRING)
    private CompetitionStage stage;

    @OneToMany
    private List<Substitution> substitutions;

    @OneToMany
    @JoinTable(name= "tbl_match_players")
    private List<Player> players;

    public Match() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public boolean isHomeMatch() {
        return homeMatch;
    }

    public void setHomeMatch(boolean homeMatch) {
        this.homeMatch = homeMatch;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getPenaltyConceded() {
        return penaltyConceded;
    }

    public void setPenaltyConceded(int penaltyConceded) {
        this.penaltyConceded = penaltyConceded;
    }

    public int getPenaltyGoalConceded() {
        return penaltyGoalConceded;
    }

    public void setPenaltyGoalConceded(int penaltyGoalConceded) {
        this.penaltyGoalConceded = penaltyGoalConceded;
    }

    public int getPenaltyWon() {
        return penaltyWon;
    }

    public void setPenaltyWon(int penaltyWon) {
        this.penaltyWon = penaltyWon;
    }

    public int getPenaltyGaolScored() {
        return penaltyGaolScored;
    }

    public void setPenaltyGaolScored(int penaltyGaolScored) {
        this.penaltyGaolScored = penaltyGaolScored;
    }

    public int getCornerConceded() {
        return cornerConceded;
    }

    public void setCornerConceded(int cornerConceded) {
        this.cornerConceded = cornerConceded;
    }

    public int getCornerWon() {
        return cornerWon;
    }

    public void setCornerWon(int cornerWon) {
        this.cornerWon = cornerWon;
    }

    public CompetitionStage getStage() {
        return stage;
    }

    public void setStage(CompetitionStage stage) {
        this.stage = stage;
    }

    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(List<Substitution> substitutions) {
        this.substitutions = substitutions;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", opponent='" + opponent + '\'' +
                ", venue='" + venue + '\'' +
                ", homeMatch=" + homeMatch +
                ", date=" + date +
                ", time=" + time +
                ", yellowCards=" + yellowCards +
                ", redCards=" + redCards +
                ", penaltyConceded=" + penaltyConceded +
                ", penaltyGoalConceded=" + penaltyGoalConceded +
                ", penaltyWon=" + penaltyWon +
                ", penaltyGaolScored=" + penaltyGaolScored +
                ", cornerConceded=" + cornerConceded +
                ", cornerWon=" + cornerWon +
                ", stage=" + stage +
                ", substitutions=" + substitutions +
                ", players=" + players +
                '}';
    }
}
