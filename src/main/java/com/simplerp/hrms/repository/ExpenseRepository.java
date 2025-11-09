package com.simplerp.hrms.repository;

import com.simplerp.hrms.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Date;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:3000")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByExpenseType(String expenseType, Pageable pageable);

    Page<Expense> findByOrganizationId(Long organizationId, Pageable pageable);

    Page<Expense> findByExpenseTypeAndOrganizationId(String expenseType, Long organizationId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
            "WHERE e.expenseType = :expenseType " +
            "AND e.createdDate = :createdDate " +
            "AND e.organizationId = :organizationId")
    Long sumAmountByExpenseTypeAndCreatedDateAndOrganizationId(
            String expenseType,
            Date createdDate,
            Long organizationId);

}