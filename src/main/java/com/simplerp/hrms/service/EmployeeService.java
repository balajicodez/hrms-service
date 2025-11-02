package com.simplerp.hrms.service;

import com.simplerp.hrms.entity.Employee;
import com.simplerp.hrms.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    public Employee getEmployeeByEmployeeNo(String employeeNo) {
        return employeeRepository.findByEmployeeNo(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with employeeNo: " + employeeNo));
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);

        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setLastName(updatedEmployee.getLastName());
        existing.setMiddleName(updatedEmployee.getMiddleName());
        existing.setEmployeeNo(updatedEmployee.getEmployeeNo());
        existing.setOrganization(updatedEmployee.getOrganization());
        existing.setEmploymentType(updatedEmployee.getEmploymentType());
        existing.setStatus(updatedEmployee.getStatus());
        existing.setOtherInfo(updatedEmployee.getOtherInfo());

        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployeeOtherInfo(Long id, Map<String, String> newInfo) {
        Employee employee = getEmployeeById(id);
        employee.setOtherInfo(newInfo);
        return employeeRepository.save(employee);
    }
}
