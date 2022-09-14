package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.entities.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

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
        month = 6;
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
    void findAllByYearAndMonthShouldReturnNonEmptyExpenseListWhenThereAreExpensesPersisted() {
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, month);
        Assertions.assertFalse(expenses.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyExpenseListWhenNoExpensesPersisted() {
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, noInputsMonth);
        Assertions.assertTrue(expenses.isEmpty());
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnNonEmptyOptionalWhenThereAreExpensesPersisted() {
        Optional<Double> totalExpenses = expenseRepository.getTotalExpensesByYearAndMonth(year, month);
        Assertions.assertFalse(totalExpenses.isEmpty());
        Assertions.assertTrue(totalExpenses.get() > 0);
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnEmptyOptionalWhenNoExpensesPersisted() {
        Optional<Double> totalExpenses = expenseRepository.getTotalExpensesByYearAndMonth(year, noInputsMonth);
        Assertions.assertTrue(totalExpenses.isEmpty());
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnNonEmptyListWhenThereAreExpensesPersisted() {
        List<ExpenseByCategoryDTO> list = expenseRepository.getTotalExpensesEachCategoryByYearAndMonth(year, month);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnEmptyListWhenNoExpensesPersisted() {
        List<ExpenseByCategoryDTO> list = expenseRepository.getTotalExpensesEachCategoryByYearAndMonth(year, noInputsMonth);
        Assertions.assertTrue(list.isEmpty());
    }
}
