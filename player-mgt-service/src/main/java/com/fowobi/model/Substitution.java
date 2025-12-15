package com.fowobi.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "tbl_substitutions")
public class Substitution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalTime matchTime;

    @OneToOne
    private Player playerIn;

    @OneToOne
    private Player playerOut;

    public Substitution() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayerIn() {
        return playerIn;
    }

    public void setPlayerIn(Player playerIn) {
        this.playerIn = playerIn;
    }

    public Player getPlayerOut() {
        return playerOut;
    }

    public void setPlayerOut(Player playerOut) {
        this.playerOut = playerOut;
    }

    public LocalTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalTime matchTime) {
        this.matchTime = matchTime;
    }

    @Override
    public String toString() {
        return "Substitution{" +
                "id=" + id +
                ", playerIn=" + playerIn +
                ", playerOut=" + playerOut +
                ", matchTime=" + matchTime +
                '}';
    }
}
