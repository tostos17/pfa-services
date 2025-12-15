package com.fowobi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_shootouts")
public class PenaltyShootOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int homePlayed;
    private int homeScored;
    private int awayPlayed;
    private int awayScored;

    public PenaltyShootOut() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHomePlayed() {
        return homePlayed;
    }

    public void setHomePlayed(int homePlayed) {
        this.homePlayed = homePlayed;
    }

    public int getHomeScored() {
        return homeScored;
    }

    public void setHomeScored(int homeScored) {
        this.homeScored = homeScored;
    }

    public int getAwayPlayed() {
        return awayPlayed;
    }

    public void setAwayPlayed(int awayPlayed) {
        this.awayPlayed = awayPlayed;
    }

    public int getAwayScored() {
        return awayScored;
    }

    public void setAwayScored(int awayScored) {
        this.awayScored = awayScored;
    }

    @Override
    public String toString() {
        return "PenaltyShootOut{" +
                "id=" + id +
                ", homePlayed=" + homePlayed +
                ", homeScored=" + homeScored +
                ", awayPlayed=" + awayPlayed +
                ", awayScored=" + awayScored +
                '}';
    }
}
