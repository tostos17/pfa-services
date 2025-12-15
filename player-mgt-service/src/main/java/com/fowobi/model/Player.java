package com.fowobi.model;

import com.fowobi.constatnt.MembershipStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_players")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String playerId;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDate dob;
    private String originState;
    private String nationality;
    private String playerPhone;
    private String playerAddress;
    private String playerEmail;
    private String squadNumber;
    private double playerHeight;
    private double playerWeight;
    private boolean hasHealthConcern;
    private String healthConcernDescription;
    private String parentFirstname;
    private String parentMiddlename;
    private String parentLastname;
    private String parentAddress;
    private String parentPhone;
    private String parentEmail;
    private String parentTitle;
    private String sponsorFirstname;
    private String sponsorLastname;
    private String sponsorTitle;
    private String sponsorOccupation;
    private String sponsorPhone;
    private String sponsorEmail;
    private LocalDate regDate;
    private String passportPhotoUrl;
    private String admissionPhotoUrl;

    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;

    @ManyToMany
//            (mappedBy = "id", cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_player_award")
    private List<Award> awards;

}

