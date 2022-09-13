package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.entities.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ExpenseRepositoryTests {

    @Autowired
    private ExpenseRepository expenseRepository;

    private String description;
    private int year;
    private int month;

    @BeforeEach
    void setUp() {
        description = "Cinema";
        year = 2022;
        month = 06;
    }

    @Test
    public void findAllByDescriptionContainingShouldReturnNonEmptyExpenseList() {
        List<Expense> expenses = expenseRepository.findAllByDescriptionContaining(description);
        Assertions.assertFalse(expenses.isEmpty());
    }

    @Test
    public void findAllByYearAndMonthShouldReturnNonEmptyExpenseList() {
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, month);
        Assertions.assertFalse(expenses.isEmpty());
    }

    @Test
    public void getTotalExpensesByYearAndMonthShouldReturnNotNullDouble() {
        Double totalExpenses = expenseRepository.getTotalExpensesByYearAndMonth(year, month);
        Assertions.assertNotNull(totalExpenses);
        Assertions.assertTrue(totalExpenses > 0);
    }

    @Test
    public void getTotalExpensesEachCategoryByYearAndMonthShouldReturnNonEmptyExpenseByCategoryDTO() {
        List<ExpenseByCategoryDTO> list = expenseRepository.getTotalExpensesEachCategoryByYearAndMonth(year, month);
        Assertions.assertFalse(list.isEmpty());
    }
}
