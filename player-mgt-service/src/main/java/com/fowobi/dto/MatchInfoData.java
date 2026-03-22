package com.fowobi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchInfoData {
    private String opponent;
    private boolean homeMatch;
    private LocalDate date;
    private LocalTime time;
}
