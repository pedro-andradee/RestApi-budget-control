package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
