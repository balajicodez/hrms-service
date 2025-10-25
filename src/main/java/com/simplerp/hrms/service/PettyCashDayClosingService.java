package com.simplerp.hrms.service;

import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.exception.PettyCashDayClosingAlreadyDoneException;
import com.simplerp.hrms.repository.PettyCashDayClosingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Service
public class PettyCashDayClosingService {

    private final PettyCashDayClosingRepository repository;

    public PettyCashDayClosingService(PettyCashDayClosingRepository repository) {
        this.repository = repository;
    }

    public PettyCashDayClosing createFromExpenses(Date closingDate, Long createdUserId) {
        // if a closing already exists, do not create
        Optional<PettyCashDayClosing> existing = repository.findByClosingDate(closingDate);
        if (existing.isPresent()) {
            throw new PettyCashDayClosingAlreadyDoneException("Closing already done for the day");
        }

        PettyCashDayClosing closing = new PettyCashDayClosing();
        closing.setClosingDate(closingDate);
        closing.setCreatedBy(createdUserId == null ? null : String.valueOf(createdUserId));

        // Try to insert a row using a single native INSERT ... SELECT that aggregates sums from Expense
        int inserted = repository.insertClosingFromExpensesByDate(closing);

        // fetch and return the row we just inserted
        return repository.findByClosingDate(closingDate)
                .orElseThrow(() -> new IllegalStateException("PettyCashDayClosing was inserted but cannot be found"));
    }

    public Optional<PettyCashDayClosing> findByDate(Date date) {
        return repository.findByClosingDate(date);
    }
}
