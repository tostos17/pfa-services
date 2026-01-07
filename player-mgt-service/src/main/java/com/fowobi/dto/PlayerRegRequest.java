package com.fowobi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Player first name is required")
    private String firstname;

    @NotBlank(message = "Player middle name is required")
    private String middlename;

    @NotBlank(message = "Player last name is required")
    private String lastname;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotBlank(message = "State of origin is required")
    private String originState;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    private String playerPhone;

    @NotBlank(message = "Player address is required")
    private String playerAddress;

    @NotBlank(message = "Player first name is required")
    private String playerEmail;

    @NotBlank(message = "Player first name is required")
    private String hasHealthConcern;

    private String healthConcernDescription;

    @NotBlank(message = "Parent title is required")
    private String parentTitle;

    @NotBlank(message = "Parent first name is required")
    private String parentFirstname;

    private String parentMiddlename;

    @NotBlank(message = "Parent last name is required")
    private String parentLastname;

    @NotBlank(message = "Parent phone number is required")
    private String parentPhone;

    @NotBlank(message = "Parent address is required")
    private String parentAddress;

    private String parentEmail;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate regDate;

}
