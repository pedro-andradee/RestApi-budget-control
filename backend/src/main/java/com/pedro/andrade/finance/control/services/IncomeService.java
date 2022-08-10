package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.entities.Income;
import com.pedro.andrade.finance.control.repositories.IncomeRepository;
import com.pedro.andrade.finance.control.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Transactional(readOnly = true)
    public List<IncomeDTO> getAllIncomes() {
        List<Income> list = incomeRepository.findAll();
        return list.stream().map(income -> new IncomeDTO(income)).toList();
    }

    @Transactional(readOnly = true)
    public IncomeDTO findById(Long id) {
        Optional<Income> optional = incomeRepository.findById(id);
        Income entity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new IncomeDTO(entity);
    }

    @Transactional
    public IncomeDTO insert(IncomeDTO dto) {
        Income entity = new Income();
        copyDtoToEntity(dto, entity);
        entity = incomeRepository.save(entity);
        return new IncomeDTO(entity);
    }
    
    @Transactional
    public IncomeDTO update(Long id, IncomeDTO dto) {
        try {
            Income entity = incomeRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = incomeRepository.save(entity);
            return new IncomeDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    private void copyDtoToEntity(IncomeDTO dto, Income entity) {
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
        entity.setDate(dto.getDate());
    }
}
