package com.pedro.andrade.finance.control.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ExpenseRepositoryTests {

    @Autowired
    private ExpenseRepository expenseRepository;


}
