package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Transactional(readOnly = true)
    public ReportDTO getReportByYearAndMonth(Integer year, Integer month) {
        Double totalIncomes = incomeService.getTotalIncomesByYearAndMonth(year, month);
        Double totalExpenses = expenseService.getTotalExpensesByYearAndMonth(year, month);
        Double finalBalance = totalIncomes - totalExpenses;
        List<ExpenseByCategoryDTO> list = expenseService.getTotalExpensesEachCategoryByYearAndMonth(year, month);

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setTotalIncomes(totalIncomes);
        reportDTO.setTotalExpenses(totalExpenses);
        reportDTO.setFinalBalance(finalBalance);
        list.forEach(exp -> reportDTO.addExpenseByCategory(exp));
        return reportDTO;
    }
}
