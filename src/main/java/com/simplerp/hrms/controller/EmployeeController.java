package com.simplerp.hrms.controller;

import com.simplerp.hrms.entity.Employee;
import com.simplerp.hrms.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/search/supervisorId")
    public ResponseEntity<Page<Employee>> getBySupervisor(
            @RequestParam("supervisorId") Long employeeId,
            Pageable pageable
    ) {
        Page<Employee> page = employeeRepository.findBySupervisorId(employeeId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search/byRegion")
    public ResponseEntity<Page<Employee>> getByRegion(
            @RequestParam("region") String region,
            Pageable pageable
    ) {
        Page<Employee> page = employeeRepository.findByRegionIgnoreCase(region, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search/bySkill")
    public ResponseEntity<Page<Employee>> getBySkill(
            @RequestParam("skill") String skill,
            Pageable pageable
    ) {
        Page<Employee> page = employeeRepository.findBySkillIgnoreCase(skill, pageable);
        return ResponseEntity.ok(page);
    }
}
