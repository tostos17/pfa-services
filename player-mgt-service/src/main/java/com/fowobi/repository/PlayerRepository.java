package com.fowobi.repository;

import com.fowobi.dto.FetchPlayerResponse;
import com.fowobi.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
            p.category,
            p.passportPhotoUrl,
            p.regDate
        )
        FROM Player p WHERE membershipStatus = 'ACTIVE'
    """)
    Page<FetchPlayerResponse> findAllPlayerSummaries(Pageable pageable);

    @Query("""
        SELECT new com.fowobi.dto.FetchPlayerResponse(
            p.playerId,
            p.firstname,
            p.middlename,
            p.lastname,
            p.dob,
            p.originState,
            p.nationality,
            p.category,
            p.passportPhotoUrl,
            p.regDate
            )
                FROM Player p WHERE p.regDate  BETWEEN :fromDate AND :toDate  AND membershipStatus = 'ACTIVE'
                    ORDER BY p.regDate DESC
    """)
    Page<FetchPlayerResponse> findPlayersByRegDate(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );

    @Query("""
        SELECT new com.fowobi.dto.FetchPlayerResponse(
            p.playerId,
            p.firstname,
            p.middlename,
            p.lastname,
            p.dob,
            p.originState,
            p.nationality,
            p.category,
            p.passportPhotoUrl,
            p.regDate
            )
                FROM Player p WHERE p.category = :category  AND membershipStatus = 'ACTIVE'
                    ORDER BY p.dob DESC
    """)
    Page<FetchPlayerResponse> findByCategory(String category, Pageable pageable);

    @Query("""
        SELECT new com.fowobi.dto.FetchPlayerResponse(
            p.playerId,
            p.firstname,
            p.middlename,
            p.lastname,
            p.dob,
            p.originState,
            p.nationality,
            p.category,
            p.passportPhotoUrl,
            p.regDate
            )
                FROM Player p WHERE p.dob BETWEEN :from AND :to AND membershipStatus = 'ACTIVE'
                    ORDER BY p.dob DESC
    """)
    Page<FetchPlayerResponse> findByDobBetween(LocalDate from, LocalDate to, Pageable pageable);

    @Query("""
        SELECT new com.fowobi.dto.FetchPlayerResponse(
            p.playerId,
            p.firstname,
            p.middlename,
            p.lastname,
            p.dob,
            p.originState,
            p.nationality,
            p.category,
            p.passportPhotoUrl,
            p.regDate
            )
                FROM Player p WHERE p.dob <= :minDate AND membershipStatus = 'ACTIVE'
                    ORDER BY p.dob DESC
    """)
    Page<FetchPlayerResponse> findByMinAge(LocalDate minDate, Pageable pageable);

    @Query("""
        SELECT new com.fowobi.dto.FetchPlayerResponse(
            p.playerId,
            p.firstname,
            p.middlename,
            p.lastname,
            p.dob,
            p.originState,
            p.nationality,
            p.category,
            p.passportPhotoUrl,
            p.regDate
            )
                FROM Player p WHERE p.dob >= :minDate AND membershipStatus = 'ACTIVE'
                    ORDER BY p.dob DESC
    """)
    Page<FetchPlayerResponse> findByMaxAge(LocalDate minDate, Pageable pageable);
}
