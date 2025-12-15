package com.fowobi.repository;

import com.fowobi.dto.FetchPlayerResponse;
import com.fowobi.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByPlayerId(String playerId);
    @Query("""
        SELECT new com.fowobi.dto.FetchPlayerResponse(
            p.playerId,
            p.firstname,
            p.middlename,
            p.lastname,
            p.dob,
            p.originState,
            p.nationality,
            p.passportPhotoUrl
        )
        FROM Player p
    """)
    Page<FetchPlayerResponse> findAllPlayerSummaries(Pageable pageable);
}
