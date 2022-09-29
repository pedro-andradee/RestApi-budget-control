package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.entities.Income;
import com.pedro.andrade.finance.control.repositories.IncomeRepository;
import com.pedro.andrade.finance.control.services.exceptions.ResourceNotFoundException;
import com.pedro.andrade.finance.control.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class IncomeServiceTests {

    @InjectMocks
    private IncomeService incomeService;

    @Mock
    private IncomeRepository incomeRepository;

    private Long existingId;
    private Long nonExistingId;
    private Income income;
    private List<Income> list;
    private Integer year;
    private Integer month;
    private Integer nonDataPersistedMonth;
    private Double totalIncomesAmount;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        income = Factory.createIncome();
        list = List.of(income);
        year = 2022;
        month = 06;
        nonDataPersistedMonth = 07;
        totalIncomesAmount = 3500.0;

        when(incomeRepository.findAll()).thenReturn(list);
        when(incomeRepository.findAllByDescriptionContaining(ArgumentMatchers.anyString())).thenReturn(list);
        when(incomeRepository.findById(existingId)).thenReturn(Optional.of(income));
        when(incomeRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(incomeRepository.findAllByYearAndMonth(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(list);
        when(incomeRepository.getTotalIncomesByYearAndMonth(year, month)).thenReturn(Optional.of(totalIncomesAmount));
        when(incomeRepository.getTotalIncomesByYearAndMonth(year, nonDataPersistedMonth)).thenReturn(Optional.empty());
        when(incomeRepository.getReferenceById(existingId)).thenReturn(income);
        doThrow(EntityNotFoundException.class).when(incomeRepository).getReferenceById(nonExistingId);
        when(incomeRepository.save(income)).thenReturn(income);
        doNothing().when(incomeRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(incomeRepository).deleteById(nonExistingId);
    }

    @Test
    void findAllIncomesShouldReturnAList() {
        List<IncomeDTO> result = incomeService.findAllIncomes();
        Assertions.assertNotNull(result);
        verify(incomeRepository, times(1)).findAll();
    }

    @Test
    void findAllByDescriptionShouldReturnAList() {
        String description = "Extra Income";
        List<IncomeDTO> result = incomeService.findAllByDescription(description);
        Assertions.assertNotNull(result);
        verify(incomeRepository, times(1)).findAllByDescriptionContaining(description);
    }

    @Test
    void findByIdShouldReturnIncomeDTOWhenIdExists() {
        IncomeDTO result = incomeService.findById(existingId);
        Assertions.assertNotNull(result);
        verify(incomeRepository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            incomeService.findById(nonExistingId);
        });
        verify(incomeRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void findAllByYearAndMonthShouldReturnAList() {
        List<IncomeDTO> result = incomeService.findAllByYearAndMonth(year, month);
        Assertions.assertNotNull(result);
        verify(incomeRepository, times(1)).findAllByYearAndMonth(year, month);
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnNonEmptyOptionalWhenThereAreIncomesPersisted() {
        Optional<Double> result = incomeService.getTotalIncomesByYearAndMonth(year, month);
        Assertions.assertFalse(result.isEmpty());
        verify(incomeRepository, times(1)).getTotalIncomesByYearAndMonth(year, month);
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnEmptyOptionalWhenNoIncomesPersisted() {
        Optional<Double> result = incomeService.getTotalIncomesByYearAndMonth(year, nonDataPersistedMonth);
        Assertions.assertTrue(result.isEmpty());
        verify(incomeRepository, times(1)).getTotalIncomesByYearAndMonth(year, nonDataPersistedMonth);
    }

    @Test
    void updateShouldReturnIncomeDTOWhenIdExists() {
        IncomeDTO dto = Factory.createIncomeDTO();
        IncomeDTO result = incomeService.update(existingId, dto);
        Assertions.assertNotNull(result);
        verify(incomeRepository, times(1)).getReferenceById(existingId);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        IncomeDTO dto = Factory.createIncomeDTO();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            incomeService.update(nonExistingId, dto);
        });

        Mockito.verify(incomeRepository, Mockito.times(1)).getReferenceById(nonExistingId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            incomeService.delete(existingId);
        });
        verify(incomeRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            incomeService.delete(nonExistingId);
        });
        verify(incomeRepository, Mockito.times(1)).deleteById(nonExistingId);
    }
}