package com.fowobi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchInfo implements InfoPackage {

    private String type;
    private MatchInfoData data;
}
