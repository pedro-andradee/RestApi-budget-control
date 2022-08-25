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

    @Query(value = "SELECT * FROM tb_expense WHERE YEAR(date) = ?1 AND MONTH(date) = ?2", nativeQuery = true)
    List<Expense> findAllByYearAndMonth(Integer year, Integer month);

    @Query(value = "SELECT SUM(value) FROM tb_expense WHERE YEAR(date) = ?1 AND MONTH(date) = ?2", nativeQuery = true)
    Double getTotalExpensesByYearAndMonth(Integer year, Integer month);

    @Query(value = "SELECT new com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO(e.category, SUM(e.value)) FROM Expense e WHERE YEAR(e.date) = ?1 AND MONTH(e.date) = ?2 GROUP BY e.category")
    List<ExpenseByCategoryDTO> getTotalExpensesEachCategoryByYearAndMonth(Integer year, Integer month);
}
