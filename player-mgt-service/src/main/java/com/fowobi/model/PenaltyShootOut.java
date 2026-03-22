package com.fowobi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_shootouts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyShootOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int homePlayed;
    private int homeScored;
    private int awayPlayed;
    private int awayScored;

    @OneToMany
    @JoinTable(name = "tbl_home_kicks")
    private List<PenaltyShot> homeKicks;

}
