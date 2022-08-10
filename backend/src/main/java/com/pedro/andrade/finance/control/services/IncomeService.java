package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.entities.Income;
import com.pedro.andrade.finance.control.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Transactional(readOnly = true)
    public List<IncomeDTO> getAllIncomes() {
        List<Income> list = incomeRepository.findAll();
        return list.stream().map(income -> new IncomeDTO(income)).toList();
    }

    @Transactional
    public IncomeDTO insert(IncomeDTO dto) {
        Income entity = new Income();
        copyDtoToEntity(dto, entity);
        entity = incomeRepository.save(entity);
        return new IncomeDTO(entity);
    }

    private void copyDtoToEntity(IncomeDTO dto, Income entity) {
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
        entity.setDate(dto.getDate());
    }
}
