package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ExpenseDTO;
import com.pedro.andrade.finance.control.repositories.ExpenseRepository;
import com.pedro.andrade.finance.control.services.exceptions.ResourceNotFoundException;
import com.pedro.andrade.finance.control.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class ExpenseServiceIntegTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalExpenses;
    private int yearWithDataPersisted;
    private int monthWithDataPersisted;
    private int yearWithoutDataPersisted;
    private int monthWithoutDataPersisted;
    private ExpenseDTO expenseDTO;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalExpenses = 5L;
        yearWithDataPersisted = 2022;
        monthWithDataPersisted = 06;
        yearWithoutDataPersisted = 2021;
        monthWithoutDataPersisted = 12;
        expenseDTO = Factory.createExpenseDTO();
    }

    @Test
    void findAllExpensesShouldReturnExpenseDTOList() {
        List<ExpenseDTO> result =  expenseService.findAllExpenses();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void findAllByDescriptionShouldReturnNonEmptyListWhenDescriptionMatches() {
        List<ExpenseDTO> result =  expenseService.findAllByDescription("Rent");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void findAllByDescriptionShouldReturnEmptyListWhenDescriptionDoesNotMatch() {
        List<ExpenseDTO> result =  expenseService.findAllByDescription("Milk");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findByIdShouldReturnExpenseDTOWhenIdExists() {
        ExpenseDTO result = expenseService.findById(existingId);
        Assertions.assertNotNull(result);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.findById(nonExistingId);
        });
    }

    @Test
    void findAllByYearAndMonthShouldReturnNonEmptyListWhenDataPersisted() {
        List<ExpenseDTO> result =  expenseService.findAllByYearAndMonth(yearWithDataPersisted, monthWithDataPersisted);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyListWhenYearWithoutDataPersisted() {
        List<ExpenseDTO> result =  expenseService.findAllByYearAndMonth(yearWithoutDataPersisted, monthWithDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyListWhenMonthWithoutDataPersisted() {
        List<ExpenseDTO> result =  expenseService.findAllByYearAndMonth(yearWithDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyListWhenNoDataPersisted() {
        List<ExpenseDTO> result =  expenseService.findAllByYearAndMonth(yearWithoutDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnNonEmptyOptionalWhenDataPersisted() {
        Optional<Double> result = expenseService.getTotalExpensesByYearAndMonth(yearWithDataPersisted, monthWithDataPersisted);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnEmptyOptionalWhenYearWithoutDataPersisted() {
        Optional<Double> result = expenseService.getTotalExpensesByYearAndMonth(yearWithoutDataPersisted, monthWithDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnEmptyOptionalWhenMonthWithoutDataPersisted() {
        Optional<Double> result = expenseService.getTotalExpensesByYearAndMonth(yearWithDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnEmptyOptionalWhenNoDataPersisted() {
        Optional<Double> result = expenseService.getTotalExpensesByYearAndMonth(yearWithoutDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnNonEmptyListWhenDataPersisted() {
        List<ExpenseByCategoryDTO> result = expenseService.getTotalExpensesEachCategoryByYearAndMonth(yearWithDataPersisted, monthWithDataPersisted);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnEmptyListWhenYearWithoutDataPersisted() {
        List<ExpenseByCategoryDTO> result = expenseService.getTotalExpensesEachCategoryByYearAndMonth(yearWithoutDataPersisted, monthWithDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnEmptyListWhenMonthWithoutDataPersisted() {
        List<ExpenseByCategoryDTO> result = expenseService.getTotalExpensesEachCategoryByYearAndMonth(yearWithDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnEmptyListWhenNoDataPersisted() {
        List<ExpenseByCategoryDTO> result = expenseService.getTotalExpensesEachCategoryByYearAndMonth(yearWithoutDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void insertShouldReturnExpenseDTO() {
        ExpenseDTO result = expenseService.insert(expenseDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void updateShouldReturnExpenseDTOWhenIdExists() {
        ExpenseDTO result = expenseService.update(existingId, expenseDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.update(nonExistingId, expenseDTO);
        });
    }

    @Test
    void deleteShouldDeleteExpenseWhenIdExists() {
        expenseService.delete(existingId);
        Assertions.assertEquals(countTotalExpenses - 1, expenseRepository.count());
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.delete(nonExistingId);
        });
    }
}