package com.fowobi.repository;

import com.fowobi.model.HealthConcern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<HealthConcern, Long> {
}
