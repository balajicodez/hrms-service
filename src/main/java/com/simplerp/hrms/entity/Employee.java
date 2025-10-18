package com.simplerp.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String skill;

    private String region;

    private int age;

    @Column(name = "is_migrant_worker")
    private boolean migrantWorker;

    // Self-referencing relationship
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    // Constructors
    public Employee() {
    }

    public Employee(String name, String skill, String region, int age, boolean migrantWorker, Employee supervisor) {
        this.name = name;
        this.skill = skill;
        this.region = region;
        this.age = age;
        this.migrantWorker = migrantWorker;
        this.supervisor = supervisor;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    public String getRegion() {
        return region;
    }

    public int getAge() {
        return age;
    }

    public boolean isMigrantWorker() {
        return migrantWorker;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMigrantWorker(boolean migrantWorker) {
        this.migrantWorker = migrantWorker;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }
}

