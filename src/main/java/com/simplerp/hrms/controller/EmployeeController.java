package com.simplerp.hrms.controller;

import com.simplerp.hrms.entity.Employee;
import com.simplerp.hrms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/empNo/{employeeNo}")
    public ResponseEntity<Employee> getEmployeeByEmployeeNo(@PathVariable String employeeNo) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmployeeNo(employeeNo));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, updatedEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Update only 'otherInfo' JSON map
    @PatchMapping("/{id}/other-info")
    public ResponseEntity<Employee> updateOtherInfo(@PathVariable Long id, @RequestBody Map<String, String> otherInfo) {
        return ResponseEntity.ok(employeeService.updateEmployeeOtherInfo(id, otherInfo));
    }
}
