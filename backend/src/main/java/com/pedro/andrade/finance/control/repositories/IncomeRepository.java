package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByDescriptionContaining(String description);
}
