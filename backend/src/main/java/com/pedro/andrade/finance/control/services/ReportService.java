package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Transactional(readOnly = true)
    public ReportDTO getReportByYearAndMonth(Integer year, Integer month) {
        Optional<Double> totalIncomes = incomeService.getTotalIncomesByYearAndMonth(year, month);
        Optional<Double> totalExpenses = expenseService.getTotalExpensesByYearAndMonth(year, month);
        List<ExpenseByCategoryDTO> list = expenseService.getTotalExpensesEachCategoryByYearAndMonth(year, month);

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setTotalIncomes(reportDTO.validateOptionalValue(totalIncomes));
        reportDTO.setTotalExpenses(reportDTO.validateOptionalValue(totalExpenses));
        Double finalBalance = reportDTO.getTotalIncomes() - reportDTO.getTotalExpenses();
        reportDTO.setFinalBalance(finalBalance);
        list.forEach(exp -> reportDTO.addExpenseByCategory(exp));
        return reportDTO;
    }
}
