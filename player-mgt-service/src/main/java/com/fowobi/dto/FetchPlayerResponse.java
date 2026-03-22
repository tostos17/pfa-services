package com.fowobi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchPlayerResponse {
    private String playerId;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDate dob;
    private String originState;
    private String nationality;
    private String category;
    private String passportPhotoUrl;

    @JsonProperty("reg_date")
    private LocalDate regDate;

}
