package com.pedro.andrade.finance.control.tests;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ExpenseDTO;
import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.dto.ReportDTO;
import com.pedro.andrade.finance.control.entities.Expense;
import com.pedro.andrade.finance.control.entities.Income;
import com.pedro.andrade.finance.control.enums.Category;

import java.time.LocalDate;

public class Factory {

    public static Expense createExpense() {
        return new Expense(1L, "Pizza", 60.0, LocalDate.parse("2022-06-11"), Category.FOOD_AND_DRINKS);
    }

    public static Income createIncome() {
        return new Income(1L, "Wage", 3000.0, LocalDate.parse("2022-06-05"));
    }

    public static IncomeDTO createIncomeDTO() {
        return new IncomeDTO(2L, "Wage", 2500.00, LocalDate.parse("2022-06-09"));
    }

    public static ExpenseDTO createExpenseDTO() {
        return new ExpenseDTO(1L, "Energy bill", 100.0, LocalDate.parse("2022-06-02"), Category.HOUSING);
    }

    public static ExpenseByCategoryDTO createExpenseByCategoryDTO() {
        return new ExpenseByCategoryDTO(Category.HOUSING, 100.0);
    }

    public static ReportDTO createReportDTO() {
        ReportDTO reportDTO = new ReportDTO(2000.0, 1000.0, 1000.0);
        reportDTO.addExpenseByCategory(Factory.createExpenseByCategoryDTO());
        return reportDTO;
    }
}
