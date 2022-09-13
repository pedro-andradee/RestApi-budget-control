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
    private String nonMatchingDescription;
    private int year;
    private int month;
    private int noInputsMonth;

    @BeforeEach
    void setUp() {
        description = "Cinema";
        nonMatchingDescription = "house";
        year = 2022;
        month = 06;
        noInputsMonth = 12;
    }

    @Test
    void findAllByDescriptionContainingShouldReturnNonEmptyExpenseListWhenDescriptionMatches() {
        List<Expense> expenses = expenseRepository.findAllByDescriptionContaining(description);
        Assertions.assertFalse(expenses.isEmpty());
    }

    @Test
    void findAllByDescriptionContainingShouldReturnEmptyExpenseListWhenDescriptionDoesNotMatch() {
        List<Expense> expenses = expenseRepository.findAllByDescriptionContaining(nonMatchingDescription);
        Assertions.assertTrue(expenses.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnNonEmptyExpenseListWhenThereAreInputs() {
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, month);
        Assertions.assertFalse(expenses.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyExpenseListWhenThereArentInputs() {
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, noInputsMonth);
        Assertions.assertTrue(expenses.isEmpty());
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnNotNullDouble() {
        Double totalExpenses = expenseRepository.getTotalExpensesByYearAndMonth(year, month);
        Assertions.assertNotNull(totalExpenses);
        Assertions.assertTrue(totalExpenses > 0);
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnNonEmptyExpenseByCategoryDTO() {
        List<ExpenseByCategoryDTO> list = expenseRepository.getTotalExpensesEachCategoryByYearAndMonth(year, month);
        Assertions.assertFalse(list.isEmpty());
    }
}
