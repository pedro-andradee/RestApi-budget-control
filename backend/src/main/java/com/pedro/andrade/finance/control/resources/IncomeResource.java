package com.pedro.andrade.finance.control.resources;

import com.pedro.andrade.finance.control.dto.IncomeDTO;
import com.pedro.andrade.finance.control.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/incomes")
public class IncomeResource {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        List<IncomeDTO> listDto = incomeService.getAllIncomes();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IncomeDTO> findById(@PathVariable Long id) {
        IncomeDTO dto = incomeService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<IncomeDTO> insert(@RequestBody @Valid IncomeDTO dto) {
        dto = incomeService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<IncomeDTO> update(@PathVariable Long id, @RequestBody @Valid IncomeDTO dto) {
        dto = incomeService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incomeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
