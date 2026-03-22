package com.fowobi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequestDto {

    @NotNull(message = "Please, provide opponent name")
    @NotBlank(message = "Please, provide valid opponent name")
    private String opponent;

    @NotNull(message = "Please, state if this is a home match")
    private boolean homeMatch;

    @NotNull(message = "Please, provide match day")
    @FutureOrPresent(message = "Match day cannot be in the past")
    private LocalDate matchDay;

    private LocalTime kickOff;
    private String matchVenue;
}
