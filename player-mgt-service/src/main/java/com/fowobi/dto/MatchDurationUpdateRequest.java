package com.fowobi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDurationUpdateRequest {

    @NotNull(message = "Kindly provide match ID")
    private Long matchId;

    @NotNull(message = "Kindly provide valid duration of each half")
    private Integer halfDuration;
}
