package com.fowobi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String organiser;
    private LocalDate startDate;
    private LocalDate endDate;
    private int homeScore;
    private int awayScore;

    @OneToOne
    private PenaltyShootOut shootOut;

    @OneToMany
    @JoinTable(name = "tbl_comp_players")
    private List<Player> player;

    @OneToMany
    @JoinTable(name = "tbl_comp_matches")
    private List<Match> matches;

    public Competition() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public PenaltyShootOut getShootOut() {
        return shootOut;
    }

    public void setShootOut(PenaltyShootOut shootOut) {
        this.shootOut = shootOut;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", organiser='" + organiser + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", shootOut=" + shootOut +
                ", player=" + player +
                ", matches=" + matches +
                '}';
    }
}

