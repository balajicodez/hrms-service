package com.simplerp.hrms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PettyCashDayClosingRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private java.sql.Date closingDate;
    private Long createdUserId;
    private String description;

    private Long tenNoteCount;
    private Long twentyNoteCount;
    private Long fiftyNoteCount;
    private Long hundredNoteCount;
    private Long twoHundredNoteCount;
    private Long fiveHundredNoteCount;

    private Long tenSoiledNoteCount;
    private Long twentySoiledNoteCount;
    private Long fiftySoiledNoteCount;
    private Long hundredSoiledNoteCount;
    private Long twoHundredSoiledNoteCount;
    private Long fiveHundredSoiledNoteCount;

    private Long oneCoinCount;
    private Long fiveCoinCount;
    private Long tenCoinCount;
    private Long twentyCoinCount;

    private Long openingBalance;
    private Long closingBalance;

    private Long organizationId;

}
