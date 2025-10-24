package com.simplerp.hrms.controller;

import com.simplerp.hrms.dto.PettyCashDayClosingRequest;
import com.simplerp.hrms.entity.PettyCashDayClosing;
import com.simplerp.hrms.service.PettyCashDayClosingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/petty-cash/day-closing")
public class PettyCashDayClosingController {

    private final PettyCashDayClosingService service;

    public PettyCashDayClosingController(PettyCashDayClosingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PettyCashDayClosing> create(@RequestBody PettyCashDayClosingRequest request) {
        PettyCashDayClosing saved = service.create(request.getClosingDate(), request.getCreatedUserId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/by-date")
    public ResponseEntity<PettyCashDayClosing> getByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<PettyCashDayClosing> found = service.findByDate(date);
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

