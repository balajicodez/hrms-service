package com.simplerp.hrms.controller;

import com.simplerp.hrms.dto.PettyCashDayClosingRequest;
import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.exception.PettyCashDayClosingAlreadyDoneException;
import com.simplerp.hrms.exception.PettyCashDayClosingBalanceException;
import com.simplerp.hrms.repository.ExpenseRepository;
import com.simplerp.hrms.service.PettyCashDayClosingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simplerp/api/petty-cash/day-closing")
public class PettyCashDayClosingController {

    private final PettyCashDayClosingService service;

    private final ExpenseRepository expenseRepository;

    public PettyCashDayClosingController(PettyCashDayClosingService service, ExpenseRepository expenseRepository) {
        this.service = service;
        this.expenseRepository = expenseRepository;
    }

    @PostMapping
    public ResponseEntity<PettyCashDayClosing> createFromExpenses(@RequestBody PettyCashDayClosingRequest request) {
        PettyCashDayClosing saved = service.createFromExpenses(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @ExceptionHandler(PettyCashDayClosingAlreadyDoneException.class)
    public ResponseEntity<String> handleAlreadyDone(PettyCashDayClosingAlreadyDoneException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PettyCashDayClosingBalanceException.class)
    public ResponseEntity<String> handleAlreadyDone(PettyCashDayClosingBalanceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @GetMapping(path="/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PettyCashDayClosing> initDayClosing(
            @RequestParam(value = "closingDate", required = false) String closingDate,
            @RequestParam(value = "organizationId", required = false) Long organizationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long openingBalance = expenseRepository.sumAmountByExpenseTypeAndCreatedDateAndOrganizationId("CASH-IN",
                java.sql.Date.valueOf(closingDate), organizationId);

        Long totalCashOut = expenseRepository.sumAmountByExpenseTypeAndCreatedDateAndOrganizationId("CASH-OUT",
                java.sql.Date.valueOf(closingDate), organizationId);


        PettyCashDayClosing closing = PettyCashDayClosing.builder().cashIn(openingBalance)
                .cashOut(openingBalance - totalCashOut).build();

        return ResponseEntity.ok(closing);
    }
}
