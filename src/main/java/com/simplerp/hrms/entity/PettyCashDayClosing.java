package com.simplerp.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class PettyCashDayClosing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date closingDate;

    private String description;

    private String createdBy;

    private Date createdTime;

    private Long cashIn;

    private Long cashOut;

}

