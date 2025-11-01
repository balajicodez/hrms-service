package com.simplerp.hrms.repository;

import com.simplerp.hrms.entity.Employee;
import com.simplerp.hrms.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:3000")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByExpenseType(String expenseType, Pageable pageable);
}