package com.simplerp.hrms.entity;

import com.simplerp.hrms.service.JpaJsonConverter;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(unique = true, nullable = false)
    private String employeeNo;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String middleName;

    @Lob
    @Convert(converter = JpaJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, String> otherInfo;

    // ✅ these must be mapped as relationships (NOT columns)
    @ManyToOne
    @JoinColumn(name = "employment_type_id")
    private MasterEntity employmentType;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private MasterEntity status;

    public Employee() {
    }
}
