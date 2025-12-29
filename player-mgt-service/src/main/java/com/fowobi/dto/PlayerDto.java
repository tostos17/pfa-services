package com.fowobi.dto;

import com.fowobi.constatnt.MembershipStatus;
import com.fowobi.model.Award;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
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

    private List<Award> awards;
}
