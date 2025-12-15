package com.fowobi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRegRequest {
    private MultipartFile photo;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDate dob;
    private String originState;
    private String nationality;
    private String playerPhone;
    private String playerAddress;
    private String playerEmail;
    private String hasHealthConcern;
    private String healthConcernDescription;
    private String parentTitle;
    private String parentFirstname;
    private String parentMiddlename;
    private String parentLastname;
    private String parentPhone;
    private String parentAddress;
    private String parentEmail;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate regDate;

}
