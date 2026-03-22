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

    @OneToMany
    @JoinTable(name = "tbl_comp_players")
    private List<Player> player;

    @OneToMany
    @JoinTable(name = "tbl_comp_matches")
    private List<Match> matches;

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
                ", player=" + player +
                ", matches=" + matches +
                '}';
    }
}

