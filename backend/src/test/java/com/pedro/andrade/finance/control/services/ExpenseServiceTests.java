package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ExpenseDTO;
import com.pedro.andrade.finance.control.entities.Expense;
import com.pedro.andrade.finance.control.repositories.ExpenseRepository;
import com.pedro.andrade.finance.control.services.exceptions.ResourceNotFoundException;
import com.pedro.andrade.finance.control.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ExpenseServiceTests {

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    private Long existingId;
    private Long nonExistingId;
    private Expense expense;
    private List<Expense> list;
    private Integer year;
    private Integer month;
    private Integer nonDataPersistedMonth;
    private Double totalExpensesAmount;
    private ExpenseByCategoryDTO expenseByCategoryDTO;
    private List<ExpenseByCategoryDTO> expenseByCategoryDTOList;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        expense = Factory.createExpense();
        list = new ArrayList<>(List.of(expense));
        year = 2022;
        month = 06;
        nonDataPersistedMonth = 07;
        totalExpensesAmount = 3500.0;
        expenseByCategoryDTO = Factory.createExpenseByCategoryDTO();
        expenseByCategoryDTOList = new ArrayList<>(List.of(expenseByCategoryDTO));

        when(expenseRepository.findAll()).thenReturn(list);
        when(expenseRepository.findAllByDescriptionContaining(ArgumentMatchers.anyString())).thenReturn(list);
        when(expenseRepository.findById(existingId)).thenReturn(Optional.of(expense));
        when(expenseRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(expenseRepository.findAllByYearAndMonth(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(list);
        when(expenseRepository.getTotalExpensesByYearAndMonth(year, month)).thenReturn(Optional.of(totalExpensesAmount));
        when(expenseRepository.getTotalExpensesByYearAndMonth(year, nonDataPersistedMonth)).thenReturn(Optional.empty());
        when(expenseRepository.getTotalExpensesEachCategoryByYearAndMonth(year, month)).thenReturn(expenseByCategoryDTOList);
        when(expenseRepository.getReferenceById(existingId)).thenReturn(expense);
        doThrow(EntityNotFoundException.class).when(expenseRepository).getReferenceById(nonExistingId);
        when(expenseRepository.save(expense)).thenReturn(expense);
        doNothing().when(expenseRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(expenseRepository).deleteById(nonExistingId);
    }

    @Test
    void findAllExpensesShouldReturnAList() {
        List<ExpenseDTO> list = expenseService.findAllExpenses();
        Assertions.assertNotNull(list);
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void findAllByDescriptionShouldReturnAList() {
        String description = "Pizza night";
        List<ExpenseDTO> list = expenseService.findAllByDescription(description);
        Assertions.assertNotNull(list);
        verify(expenseRepository, times(1)).findAllByDescriptionContaining(description);
    }

    @Test
    void findByIdShouldReturnExpenseDTOWhenIdExists() {
        ExpenseDTO result = expenseService.findById(existingId);
        Assertions.assertNotNull(result);
        verify(expenseRepository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.findById(nonExistingId);
        });
        verify(expenseRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void findAllByYearAndMonthShouldReturnAList() {
        List<ExpenseDTO> result = expenseService.findAllByYearAndMonth(year, month);
        Assertions.assertNotNull(result);
        verify(expenseRepository, times(1)).findAllByYearAndMonth(year, month);
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnNonEmptyOptionalWhenThereAreIncomesPersisted() {
        Optional<Double> result = expenseService.getTotalExpensesByYearAndMonth(year, month);
        Assertions.assertFalse(result.isEmpty());
        verify(expenseRepository, times(1)).getTotalExpensesByYearAndMonth(year, month);
    }

    @Test
    void getTotalExpensesByYearAndMonthShouldReturnEmptyOptionalWhenNoIncomesPersisted() {
        Optional<Double> result = expenseService.getTotalExpensesByYearAndMonth(year, nonDataPersistedMonth);
        Assertions.assertTrue(result.isEmpty());
        verify(expenseRepository, times(1)).getTotalExpensesByYearAndMonth(year, nonDataPersistedMonth);
    }

    @Test
    void getTotalExpensesEachCategoryByYearAndMonthShouldReturnAList() {
        List<ExpenseByCategoryDTO> result = expenseService.getTotalExpensesEachCategoryByYearAndMonth(year, month);
        Assertions.assertNotNull(result);
        verify(expenseRepository, times(1)).getTotalExpensesEachCategoryByYearAndMonth(year, month);
    }

    @Test
    void updateShouldReturnExpenseDTOWhenIdExists() {
        ExpenseDTO dto = Factory.createExpenseDTO();
        ExpenseDTO result = expenseService.update(existingId, dto);
        Assertions.assertNotNull(result);
        verify(expenseRepository, times(1)).getReferenceById(existingId);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        ExpenseDTO dto = Factory.createExpenseDTO();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.update(nonExistingId, dto);
        });
        verify(expenseRepository, times(1)).getReferenceById(nonExistingId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            expenseService.delete(existingId);
        });
        verify(expenseRepository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.delete(nonExistingId);
        });
        verify(expenseRepository, times(1)).deleteById(nonExistingId);
    }
}