package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByDescriptionContaining(String description);

    @Query(value = "SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findAllByYearAndMonth(Integer year, Integer month);

    @Query(value = "SELECT SUM(e.amount) FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    Double getTotalExpensesByYearAndMonth(Integer year, Integer month);

    @Query(value = "SELECT new com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO(e.category, SUM(e.amount)) FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month GROUP BY e.category")
    List<ExpenseByCategoryDTO> getTotalExpensesEachCategoryByYearAndMonth(Integer year, Integer month);
}
