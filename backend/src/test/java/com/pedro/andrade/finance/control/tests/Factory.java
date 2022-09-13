package com.pedro.andrade.finance.control.tests;

import com.pedro.andrade.finance.control.entities.Expense;
import com.pedro.andrade.finance.control.enums.Category;

import java.time.LocalDate;

public class Factory {

    public static Expense createExpense() {
        return new Expense(1L, "Pizza", 60.0, LocalDate.parse("2022-06-11"), Category.FOOD_AND_DRINKS);
    }
}
