package com.simplerp.hrms.repository;

import com.simplerp.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:3000")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findBySupervisorId(Long supervisorId, Pageable pageable);

    Page<Employee> findByRegionIgnoreCase(String region, Pageable pageable);

    Page<Employee> findBySkillIgnoreCase(String skill, Pageable pageable);
}