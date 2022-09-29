package com.pedro.andrade.finance.control.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("total_incomes")
    private Double totalIncomes;
    @JsonProperty("total_expenses")
    private Double totalExpenses;
    @JsonProperty("final_balance")
    private Double finalBalance;
    @JsonProperty("total_expenses_by_category")
    private List<ExpenseByCategoryDTO> totalExpensesByCategory = new ArrayList<>();

    public ReportDTO() {
    }

    public Double getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(Double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public List<ExpenseByCategoryDTO> getTotalExpensesByCategory() {
        return totalExpensesByCategory;
    }

    public void addExpenseByCategory(ExpenseByCategoryDTO expenseByCategoryDTO) {
        this.totalExpensesByCategory.add(expenseByCategoryDTO);
    }

    public double validateOptionalValue(Optional<Double> total) {
        if (total.isEmpty()) {
            return 0.0;
        }
        return total.get();
    }
}
