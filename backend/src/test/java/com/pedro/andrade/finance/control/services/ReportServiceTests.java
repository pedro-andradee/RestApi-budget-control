package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ReportDTO;
import com.pedro.andrade.finance.control.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReportServiceTests {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private IncomeService incomeService;

    private List<ExpenseByCategoryDTO> list;
    private Integer year;
    private Integer month;
    private Double amount;

    @BeforeEach
    void setUp() {
        list = List.of(Factory.createExpenseByCategoryDTO());
        year = 2022;
        month = 06;
        amount = 2000.0;
        when(incomeService.getTotalIncomesByYearAndMonth(year, month)).thenReturn(Optional.of(amount));
        when(expenseService.getTotalExpensesByYearAndMonth(year, month)).thenReturn(Optional.of(amount));
        when(expenseService.getTotalExpensesEachCategoryByYearAndMonth(year, month)).thenReturn(list);
    }

    @Test
    void getReportByYearAndMonthShouldReturnReportDTO() {
        ReportDTO result = reportService.getReportByYearAndMonth(year, month);
        Assertions.assertNotNull(result);
    }
}