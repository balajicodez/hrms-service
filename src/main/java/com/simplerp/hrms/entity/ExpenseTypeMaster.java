package com.simplerp.hrms.entity;

import com.simplerp.hrms.enumeration.ExpenseSubTypeEnum;
import com.simplerp.hrms.enumeration.ExpenseTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class ExpenseTypeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExpenseTypeEnum type;

    @Enumerated(EnumType.STRING)
    private ExpenseSubTypeEnum subtype;

}

