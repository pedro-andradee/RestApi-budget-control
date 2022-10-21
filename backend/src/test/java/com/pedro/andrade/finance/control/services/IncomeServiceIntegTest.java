package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.repositories.IncomeRepository;
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
class IncomeServiceIntegTest {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private IncomeRepository incomeRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalIncomes;
    private int yearWithDataPersisted;
    private int monthWithDataPersisted;
    private int yearWithoutDataPersisted;
    private int monthWithoutDataPersisted;
    private IncomeDTO incomeDTO;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalIncomes = 3L;
        yearWithDataPersisted = 2022;
        monthWithDataPersisted = 06;
        yearWithoutDataPersisted = 2021;
        monthWithoutDataPersisted = 12;
        incomeDTO = Factory.createIncomeDTO();
    }

    @Test
    void findAllIncomesShouldReturnIncomeDTOList() {
        List<IncomeDTO> result = incomeService.findAllIncomes();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void findAllByDescriptionShouldReturnNonEmptyListWhenDescriptionMatches() {
        List<IncomeDTO> result = incomeService.findAllByDescription("Wage");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void findAllByDescriptionShouldReturnEmptyListWhenDescriptionDoesNotMatch() {
        List<IncomeDTO> result = incomeService.findAllByDescription("Salary");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findByIdShouldReturnIncomeDTOWhenIdExists() {
        IncomeDTO result = incomeService.findById(existingId);
        Assertions.assertNotNull(result);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            incomeService.findById(nonExistingId);
        });
    }

    @Test
    void findAllByYearAndMonthShouldReturnNonEmptyListWhenDataPersisted() {
        List<IncomeDTO> result = incomeService.findAllByYearAndMonth(yearWithDataPersisted, monthWithDataPersisted);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyListWhenYearWithoutDataPersisted() {
        List<IncomeDTO> result = incomeService.findAllByYearAndMonth(yearWithoutDataPersisted, monthWithDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyListWhenMonthWithoutDataPersisted() {
        List<IncomeDTO> result = incomeService.findAllByYearAndMonth(yearWithDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyListWhenNoDataPersisted() {
        List<IncomeDTO> result = incomeService.findAllByYearAndMonth(yearWithoutDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnNonEmptyOptionalWhenDataPersisted() {
        Optional<Double> result = incomeService.getTotalIncomesByYearAndMonth(yearWithDataPersisted, monthWithDataPersisted);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnEmptyOptionalWhenYearWithoutDataPersisted() {
        Optional<Double> result = incomeService.getTotalIncomesByYearAndMonth(yearWithoutDataPersisted, monthWithDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnEmptyOptionalWhenMonthWithoutDataPersisted() {
        Optional<Double> result = incomeService.getTotalIncomesByYearAndMonth(yearWithDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnEmptyOptionalWhenNoDataPersisted() {
        Optional<Double> result = incomeService.getTotalIncomesByYearAndMonth(yearWithoutDataPersisted, monthWithoutDataPersisted);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void insertShouldReturnIncomeDTO() {
        IncomeDTO result = incomeService.insert(incomeDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void updateShouldReturnIncomeDTOWhenIdExists() {
        IncomeDTO result = incomeService.update(existingId, incomeDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            incomeService.update(nonExistingId, incomeDTO);
        });
    }

    @Test
    void deleteShouldDeleteIncomeWhenIdExists() {
        incomeService.delete(existingId);
        Assertions.assertEquals(countTotalIncomes - 1, incomeRepository.count());
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            incomeService.delete(nonExistingId);
        });
    }
}