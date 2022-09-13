package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByDescriptionContaining(String description);

    @Query(value = "SELECT i FROM Income i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month")
    List<Income> findAllByYearAndMonth(Integer year, Integer month);

    @Query(value = "SELECT SUM(i.amount) FROM Income i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month")
    Double getTotalIncomesByYearAndMonth(Integer year, Integer month);
}
