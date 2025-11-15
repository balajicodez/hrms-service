package com.simplerp.hrms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PettyCashDayClosing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date closingDate;

    private String description;

    private String createdBy;

    private Date createdTime;

    private String comments;

    private Long closingBalance;

    private Long _10NoteCount;
    private Long _20NoteCount;
    private Long _50NoteCount;
    private Long _100NoteCount;
    private Long _200NoteCount;
    private Long _500NoteCount;

    private Long _10SoiledNoteCount;
    private Long _20SoiledNoteCount;
    private Long _50SoiledNoteCount;
    private Long _100SoiledNoteCount;
    private Long _200SoiledNoteCount;
    private Long _500SoiledNoteCount;

    private Long _1CoinCount;
    private Long _5CoinCount;
    private Long _10CoinCount;
    private Long _20CoinCount;


    private Long cashIn;

    private Long cashOut;

    private Long organizationId;
    private String organizationName;

}


