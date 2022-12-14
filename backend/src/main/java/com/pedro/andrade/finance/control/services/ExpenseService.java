package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ExpenseByCategoryDTO;
import com.pedro.andrade.finance.control.dto.ExpenseDTO;
import com.pedro.andrade.finance.control.entities.Expense;
import com.pedro.andrade.finance.control.enums.Category;
import com.pedro.andrade.finance.control.repositories.ExpenseRepository;
import com.pedro.andrade.finance.control.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findAllExpenses() {
        List<Expense> list = expenseRepository.findAll();
        return list.stream().map(expense -> new ExpenseDTO(expense)).toList();
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findAllByDescription(String description) {
        List<Expense> list = expenseRepository.findAllByDescriptionContaining(description);
        return list.stream().map(expense -> new ExpenseDTO(expense)).toList();
    }

    @Transactional(readOnly = true)
    public ExpenseDTO findById(Long id) {
        Optional<Expense> optional = expenseRepository.findById(id);
        Expense entity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ExpenseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findAllByYearAndMonth(Integer year, Integer month) {
        List<Expense> list = expenseRepository.findAllByYearAndMonth(year, month);
        return list.stream().map(expense -> new ExpenseDTO(expense)).toList();
    }

    @Transactional(readOnly = true)
    public Optional<Double> getTotalExpensesByYearAndMonth(Integer year, Integer month) {
        return expenseRepository.getTotalExpensesByYearAndMonth(year, month);
    }

    @Transactional(readOnly = true)
    public List<ExpenseByCategoryDTO> getTotalExpensesEachCategoryByYearAndMonth(Integer year, Integer month) {
        return expenseRepository.getTotalExpensesEachCategoryByYearAndMonth(year, month);
    }

    @Transactional
    public ExpenseDTO insert(ExpenseDTO dto) {
        Expense entity = new Expense();
        copyDtoToEntity(dto, entity);
        entity = expenseRepository.save(entity);
        return new ExpenseDTO(entity);
    }

    @Transactional
    public ExpenseDTO update(Long id, ExpenseDTO dto) {
        try {
            Expense entity = expenseRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = expenseRepository.save(entity);
            return new ExpenseDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    public void delete(Long id) {
        try {
            expenseRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    private void copyDtoToEntity(ExpenseDTO dto, Expense entity) {
        entity.setDescription(dto.getDescription());
        entity.setAmount(dto.getAmount());
        entity.setDate(dto.getDate());
        if (dto.getCategory() != null) {
            entity.setCategory(dto.getCategory());
        } else {
            entity.setCategory(Category.OTHERS);
        }
    }
}
