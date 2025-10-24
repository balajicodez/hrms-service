package com.simplerp.hrms.service;

import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.repository.PettyCashDayClosingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PettyCashDayClosingService {

    private final PettyCashDayClosingRepository repository;

    public PettyCashDayClosingService(PettyCashDayClosingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PettyCashDayClosing create(LocalDate closingDate, Long createdUserId) {
        PettyCashDayClosing entity = new PettyCashDayClosing();
        entity.setClosingDate(closingDate);
        entity.setCreatedBy(createdUserId == null ? null : String.valueOf(createdUserId));
        entity.setCreatedTime(LocalDateTime.now());
        entity.setCashIn(0L);
        entity.setCashOut(0L);
        return repository.save(entity);
    }

    public Optional<PettyCashDayClosing> findByDate(LocalDate date) {
        return repository.findByClosingDate(date);
    }
}

