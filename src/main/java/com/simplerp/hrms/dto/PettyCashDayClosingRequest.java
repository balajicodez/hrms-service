package com.simplerp.hrms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PettyCashDayClosingRequest {
    private LocalDate closingDate;
    private Long createdUserId;
}

