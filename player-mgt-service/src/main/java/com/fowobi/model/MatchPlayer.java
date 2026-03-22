package com.fowobi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_match_player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private int jerseyNumber;
    private boolean substituted;
}
