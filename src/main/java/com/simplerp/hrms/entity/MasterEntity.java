package com.simplerp.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // ✅ Relationship mapping if MasterType is another entity
    @ManyToOne
    @JoinColumn(name = "master_type_id")
    private MasterType masterType;
}
