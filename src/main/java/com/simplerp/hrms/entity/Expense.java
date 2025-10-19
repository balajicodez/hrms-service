package com.simplerp.hrms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Long amount;

    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "expense_type")
    private ExpenseTypeMaster expenseType;
}

