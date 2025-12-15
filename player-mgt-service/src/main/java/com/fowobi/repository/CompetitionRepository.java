package com.fowobi.repository;

import com.fowobi.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
