package com.simplerp.hrms.repository;

import com.simplerp.hrms.entity.PettyCashDayClosing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:3000")
public interface PettyCashDayClosingRepository extends JpaRepository<PettyCashDayClosing, Long> {
    Optional<PettyCashDayClosing> findByClosingDate(java.sql.Date closingDate);

    /**
     * Insert a PettyCashDayClosing row for the given date using aggregated sums from the Expense table.
     * Accepts a PettyCashDayClosing object; its fields are referenced in the native query via SpEL.
     * Returns the number of rows inserted (1 if inserted, 0 otherwise).
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO petty_cash_day_closing (closing_date, description, created_by, created_time, cash_in, cash_out) " +
            "SELECT :#{#closing.closingDate}, :#{#closing.description}, :#{#closing.createdBy}, CURRENT_TIMESTAMP, " +
            "COALESCE(SUM(CASE WHEN UPPER(expense_type) = 'CASH-IN' THEN amount ELSE 0 END),0), " +
            "COALESCE(SUM(CASE WHEN UPPER(expense_type) = 'CASH-OUT' THEN amount ELSE 0 END),0) " +
            "FROM expense WHERE created_date = :#{#closing.closingDate}",
            nativeQuery = true)
    int insertClosingFromExpensesByDate(PettyCashDayClosing closing);
}
