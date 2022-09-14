package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Income;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class IncomeRepositoryTests {

    @Autowired
    private IncomeRepository incomeRepository;
    private String description;
    private String nonMatchingDescription;
    private int year;
    private int month;
    private int noInputsMonth;

    @BeforeEach
    void setUp() {
        description = "Wage";
        nonMatchingDescription = "Lottery";
        year = 2022;
        month = 6;
        noInputsMonth = 12;
    }

    @Test
    void findAllByDescriptionContainingShouldReturnNonEmptyIncomeListWhenDescriptionMatches() {
        List<Income> incomes = incomeRepository.findAllByDescriptionContaining(description);
        Assertions.assertFalse(incomes.isEmpty());
    }

    @Test
    public void findAllByDescriptionContainingShouldReturnEmptyIncomeListWhenDescriptionDoesNotMatch() {
        List<Income> incomes = incomeRepository.findAllByDescriptionContaining(nonMatchingDescription);
        Assertions.assertTrue(incomes.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnNonEmptyIncomeListWhenThereAreIncomesPersisted() {
        List<Income> incomes = incomeRepository.findAllByYearAndMonth(year, month);
        Assertions.assertFalse(incomes.isEmpty());
    }

    @Test
    void findAllByYearAndMonthShouldReturnEmptyIncomeListWhenNoIncomesPersisted() {
        List<Income> incomes = incomeRepository.findAllByYearAndMonth(year, noInputsMonth);
        Assertions.assertTrue(incomes.isEmpty());
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnNonEmptyOptionalWhenThereAreIncomesPersisted() {
        Optional<Double> totalIncomes = incomeRepository.getTotalIncomesByYearAndMonth(year, month);
        Assertions.assertFalse(totalIncomes.isEmpty());
        Assertions.assertTrue(totalIncomes.get() > 0);
    }

    @Test
    void getTotalIncomesByYearAndMonthShouldReturnEmptyOptionalWhenNoIncomesPersisted() {
        Optional<Double> totalIncomes = incomeRepository.getTotalIncomesByYearAndMonth(year, noInputsMonth);
        Assertions.assertTrue(totalIncomes.isEmpty());
    }
}