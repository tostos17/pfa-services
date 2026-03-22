package com.fowobi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_penalties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyShot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private MatchPlayer taker;
    private boolean scored;
    private int penaltyNumber;
}
