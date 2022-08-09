package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
