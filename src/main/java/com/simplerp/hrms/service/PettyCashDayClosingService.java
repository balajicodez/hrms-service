package com.simplerp.hrms.service;

import com.simplerp.hrms.dto.PettyCashDayClosingRequest;
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

    public PettyCashDayClosing createFromExpenses(PettyCashDayClosingRequest request) {
        // if a closing already exists, do not create
        Optional<PettyCashDayClosing> existing = repository.findByClosingDate(request.getClosingDate());
        if (existing.isPresent()) {
            throw new PettyCashDayClosingAlreadyDoneException("Closing already done for the day");
        }

        PettyCashDayClosing closing = new PettyCashDayClosing();
        closing.setClosingDate(request.getClosingDate());
        closing.setCreatedBy(request.getCreatedUserId() == null ? null : String.valueOf(request.getCreatedUserId()));
        closing.set_1CoinCount(request.getOneCoinCount());
        closing.set_5CoinCount(request.getFiveCoinCount());
        closing.set_10CoinCount(request.getTenCoinCount());
        closing.set_20CoinCount(request.getTwentyCoinCount());
        closing.set_10NoteCount(request.getTenNoteCount());
        closing.set_20NoteCount(request.getTwentyNoteCount());
        closing.set_50NoteCount(request.getFiftyNoteCount());
        closing.set_100NoteCount(request.getHundredNoteCount());
        closing.set_200NoteCount(request.getTwoHundredNoteCount());
        closing.set_500NoteCount(request.getFiveHundredNoteCount());
        closing.set_10SoiledNoteCount(request.getTenSoiledNoteCount());
        closing.set_20SoiledNoteCount(request.getTwentySoiledNoteCount());
        closing.set_50SoiledNoteCount(request.getFiftySoiledNoteCount());
        closing.set_100SoiledNoteCount(request.getHundredSoiledNoteCount());
        closing.set_200SoiledNoteCount(request.getTwoHundredSoiledNoteCount());
        closing.set_500SoiledNoteCount(request.getFiveHundredSoiledNoteCount());
        closing.setDescription(request.getDescription());
        closing.setCashIn(request.getOneCoinCount() +
                request.getFiveCoinCount() * 5 +
                request.getTenCoinCount() * 10 +
                request.getTwentyCoinCount() * 20 +
                request.getTenNoteCount() * 10 +
                request.getTwentyNoteCount() * 20 +
                request.getFiftyNoteCount() * 50 +
                request.getHundredNoteCount() * 100 +
                request.getTwoHundredNoteCount() * 200 +
                request.getFiveHundredNoteCount() * 500 +
                request.getTenSoiledNoteCount() * 10 +
                request.getTwentySoiledNoteCount() * 20 +
                request.getFiftySoiledNoteCount() * 50 +
                request.getHundredSoiledNoteCount() * 100 +
                request.getFiveHundredSoiledNoteCount() * 500);

        closing.setCashOut(request.getCashOut());
        // Try to insert a row using a single native INSERT ... SELECT that aggregates sums from Expense
        repository.save(closing);

        // fetch and return the row we just inserted
        return repository.findByClosingDate(request.getClosingDate())
                .orElseThrow(() -> new IllegalStateException("PettyCashDayClosing was inserted but cannot be found"));
    }

    public Optional<PettyCashDayClosing> findByDate(Date date) {
        return repository.findByClosingDate(date);
    }
}
