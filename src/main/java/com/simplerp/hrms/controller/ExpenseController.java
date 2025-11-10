package com.simplerp.hrms.controller;

import com.simplerp.hrms.entity.Expense;
import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.exception.PettyCashDayClosingAlreadyDoneException;
import com.simplerp.hrms.repository.ExpenseRepository;
import com.simplerp.hrms.repository.PettyCashDayClosingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/simplerp/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    private final PettyCashDayClosingRepository pettyCashDayClosingRepository;

    /**
     * Accepts a multipart request with a JSON part named "expense" and an optional file part named "file".
     * Client must set the "expense" part Content-Type to application/json.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Expense> createFromJsonAndFile(
            @RequestPart("expense") Expense expense,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) throws IOException {
        if (image != null && !image.isEmpty()) {
            expense.setImageData(image.getBytes());
            expense.setImageFileName(image.getOriginalFilename());
            expense.setImageContentType(image.getContentType());
        }

        Expense saved = expenseRepository.save(expense);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Expense> updateExpense(
            @RequestPart("expense") Expense expense,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) throws IOException {

        Optional<PettyCashDayClosing> existing = pettyCashDayClosingRepository.findByClosingDate(expense.getCreatedDate());

        if (existing.isPresent()) {
            throw new PettyCashDayClosingAlreadyDoneException("Closing already done for the day");
        }

        if (image != null && !image.isEmpty()) {
            expense.setImageData(image.getBytes());
            expense.setImageFileName(image.getOriginalFilename());
            expense.setImageContentType(image.getContentType());
        }

        Expense saved = expenseRepository.save(expense);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    /**
     * Returns a paginated list of expenses.
     * Example: GET /simplerp/api/expenses?page=0&size=20
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Expense>> listExpenses(
            @RequestParam(value = "expenseType", required = false) String expenseType,
            @RequestParam(value = "organizationId", required = false) Long organizationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        Page<Expense> expenses = Page.empty();
        if (organizationId == null && expenseType == null) {
            expenses = expenseRepository.findAll(PageRequest.of(page, size));
        } else {
            if (expenseType == null && organizationId != null) {
                expenses = expenseRepository.findByOrganizationId(organizationId, PageRequest.of(page, size));
            } else if (organizationId == null && expenseType != null) {
                expenses = expenseRepository.findByExpenseType(expenseType, PageRequest.of(page, size));
            } else {
                expenses = expenseRepository.findByExpenseTypeAndOrganizationId(expenseType, organizationId, PageRequest.of(page, size));
            }
        }
        return ResponseEntity.ok(expenses);
    }
}
