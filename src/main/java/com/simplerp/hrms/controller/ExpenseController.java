package com.simplerp.hrms.controller;

import com.simplerp.hrms.entity.Expense;
import com.simplerp.hrms.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/simplerp/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

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
            if (expenseType == null) {
                expenses = expenseRepository.findByOrganizationId(organizationId, PageRequest.of(page, size));
            } else if (organizationId == null) {
                expenses = expenseRepository.findByExpenseType(expenseType, PageRequest.of(page, size));
            }
        }
        return ResponseEntity.ok(expenses);
    }
}
