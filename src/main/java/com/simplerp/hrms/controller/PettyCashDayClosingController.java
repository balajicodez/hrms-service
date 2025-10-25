package com.simplerp.hrms.controller;

import com.simplerp.hrms.dto.PettyCashDayClosingRequest;
import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.exception.PettyCashDayClosingAlreadyDoneException;
import com.simplerp.hrms.service.PettyCashDayClosingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simplerp/api/petty-cash/day-closing")
public class PettyCashDayClosingController {

    private final PettyCashDayClosingService service;

    public PettyCashDayClosingController(PettyCashDayClosingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PettyCashDayClosing> createFromExpenses(@RequestBody PettyCashDayClosingRequest request) {
        PettyCashDayClosing saved = service.createFromExpenses(request.getClosingDate(), request.getCreatedUserId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @ExceptionHandler(PettyCashDayClosingAlreadyDoneException.class)
    public ResponseEntity<String> handleAlreadyDone(PettyCashDayClosingAlreadyDoneException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
