package com.simplerp.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String referenceNumber;

    private Long amount;

    private Long employeeId;

    private Date createdDate;

    private Date transactionDate;

    private String createdByUser;

    private String createdByUserId;

    private String expenseType;

    private String expenseSubType;

    private String branch;

    private Long branchId;

    // Binary image data (stored as BLOB)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data", columnDefinition = "BLOB")
    private byte[] imageData;

    // Original file name
    private String imageFileName;

    // MIME type (e.g. image/png)
    private String imageContentType;
}

