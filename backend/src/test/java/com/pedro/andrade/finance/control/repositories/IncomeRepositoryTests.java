package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Income;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class IncomeRepositoryTests {

    @Autowired
    private IncomeRepository incomeRepository;
    private String description;
    private int year;
    private int month;

    @BeforeEach
    void setUp() {
        description = "Wage";
        year = 2022;
        month = 06;
    }

    @Test
    void findAllByDescriptionContainingShouldReturnNonEmptyIncomeList() {
        List<Income> incomes = incomeRepository.findAllByDescriptionContaining(description);
        Assertions.assertFalse(incomes.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnNonEmptyIncomeList() {
        List<Income> incomes = incomeRepository.findAllByYearAndMonth(year, month);
        Assertions.assertFalse(incomes.isEmpty());
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnNotNullDouble() {
        Double totalIncomes = incomeRepository.getTotalIncomesByYearAndMonth(year, month);
        Assertions.assertNotNull(totalIncomes);
        Assertions.assertTrue(totalIncomes > 0);
    }
}