package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByDescriptionContaining(String description);

    @Query(value = "SELECT * FROM tb_income WHERE YEAR(date) = ?1 AND MONTH(date) = ?2", nativeQuery = true)
    List<Income> findAllByYearAndMonth(Integer year, Integer month);
}
