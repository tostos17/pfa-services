package com.fowobi.service;

import com.fowobi.api.ApiResponse;
import com.fowobi.dto.MatchDto;
import com.fowobi.dto.MatchDurationUpdateRequest;
import com.fowobi.dto.MatchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchService {
    ApiResponse<String> addMatch(MatchRequestDto matchRequestDto);
    ApiResponse<Page<MatchDto>> fetchAll(Pageable pageable);

    ApiResponse<Page<MatchDto>> getAllResults(Pageable pageable);

    ApiResponse<String> setMatchDuration(MatchDurationUpdateRequest request);
}
