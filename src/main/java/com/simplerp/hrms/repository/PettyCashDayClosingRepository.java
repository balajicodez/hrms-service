package com.simplerp.hrms.repository;

import com.simplerp.hrms.entity.PettyCashDayClosing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PettyCashDayClosingRepository extends JpaRepository<PettyCashDayClosing, Long> {
    Optional<PettyCashDayClosing> findByClosingDate(LocalDate closingDate);
}

