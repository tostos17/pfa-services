package com.fowobi.repository;

import com.fowobi.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AwardRepository extends JpaRepository<Award, Long> {
}
