package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Expense;
import com.pedro.andrade.finance.control.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByDescriptionContaining(String description);

    @Query(value = "SELECT * FROM tb_expense WHERE YEAR(date) = ?1 AND MONTH(date) = ?2", nativeQuery = true)
    List<Expense> findAllByYearAndMonth(Integer year, Integer month);
}
