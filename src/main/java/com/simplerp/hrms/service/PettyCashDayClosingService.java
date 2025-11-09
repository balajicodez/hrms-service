package com.simplerp.hrms.service;

import com.simplerp.hrms.dto.PettyCashDayClosingRequest;
import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.exception.PettyCashDayClosingAlreadyDoneException;
import com.simplerp.hrms.exception.PettyCashDayClosingBalanceException;
import com.simplerp.hrms.repository.PettyCashDayClosingRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class PettyCashDayClosingService {

    private final PettyCashDayClosingRepository repository;

    public PettyCashDayClosingService(PettyCashDayClosingRepository repository) {
        this.repository = repository;
    }

    public PettyCashDayClosing createFromExpenses(PettyCashDayClosingRequest request) {
        // if a closing already exists, do not create
        Optional<PettyCashDayClosing> existing = repository.findByClosingDate(request.getClosingDate());
        if (existing.isPresent()) {
            throw new PettyCashDayClosingAlreadyDoneException("Closing already done for the day");
        }

        // compute cashIn safely (treat null counts as 0)
        long cashIn = safe(request.getOneCoinCount()) +
                safe(request.getFiveCoinCount()) * 5 +
                safe(request.getTenCoinCount()) * 10 +
                safe(request.getTwentyCoinCount()) * 20 +
                safe(request.getTenNoteCount()) * 10 +
                safe(request.getTwentyNoteCount()) * 20 +
                safe(request.getFiftyNoteCount()) * 50 +
                safe(request.getHundredNoteCount()) * 100 +
                safe(request.getTwoHundredNoteCount()) * 200 +
                safe(request.getFiveHundredNoteCount()) * 500 +
                safe(request.getTenSoiledNoteCount()) * 10 +
                safe(request.getTwentySoiledNoteCount()) * 20 +
                safe(request.getFiftySoiledNoteCount()) * 50 +
                safe(request.getHundredSoiledNoteCount()) * 100 +
                safe(request.getFiveHundredSoiledNoteCount()) * 500;

        if (cashIn != (request.getOpeningBalance() - request.getClosingBalance())) {
            throw new PettyCashDayClosingBalanceException("Calculated closing balance does not match the provided Denominations");
        }

        Long cashOut = request.getClosingBalance();
        String createdBy = request.getCreatedUserId() == null ? null : String.valueOf(request.getCreatedUserId());

        PettyCashDayClosing closing = PettyCashDayClosing.builder()
                .closingDate(request.getClosingDate())
                .createdBy(createdBy)
                ._1CoinCount(request.getOneCoinCount())
                ._5CoinCount(request.getFiveCoinCount())
                ._10CoinCount(request.getTenCoinCount())
                ._20CoinCount(request.getTwentyCoinCount())
                ._10NoteCount(request.getTenNoteCount())
                ._20NoteCount(request.getTwentyNoteCount())
                ._50NoteCount(request.getFiftyNoteCount())
                ._100NoteCount(request.getHundredNoteCount())
                ._200NoteCount(request.getTwoHundredNoteCount())
                ._500NoteCount(request.getFiveHundredNoteCount())
                ._10SoiledNoteCount(request.getTenSoiledNoteCount())
                ._20SoiledNoteCount(request.getTwentySoiledNoteCount())
                ._50SoiledNoteCount(request.getFiftySoiledNoteCount())
                ._100SoiledNoteCount(request.getHundredSoiledNoteCount())
                ._200SoiledNoteCount(request.getTwoHundredSoiledNoteCount())
                ._500SoiledNoteCount(request.getFiveHundredSoiledNoteCount())
                .description(request.getDescription())
                .cashIn(cashIn)
                .cashOut(cashOut)
                .build();

        // Try to insert a row using a single native INSERT ... SELECT that aggregates sums from Expense
        repository.save(closing);

        // fetch and return the row we just inserted
        return repository.findByClosingDate(request.getClosingDate())
                .orElseThrow(() -> new IllegalStateException("PettyCashDayClosing was inserted but cannot be found"));
    }

    // helper to treat null as zero
    private long safe(Long v) {
        return v == null ? 0L : v;
    }

    public Optional<PettyCashDayClosing> findByDate(Date date) {
        return repository.findByClosingDate(date);
    }
}
