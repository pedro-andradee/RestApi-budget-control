package com.pedro.andrade.finance.control.resources;

import com.pedro.andrade.finance.control.dto.ExpenseDTO;
import com.pedro.andrade.finance.control.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseResource {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> findAllExpenses(@RequestParam(value = "description", required = false) String description) {
        List<ExpenseDTO> listDto;
        if (description != null) {
            listDto = expenseService.findAllByDescription(description);
        } else {
            listDto = expenseService.findAllExpenses();
        }
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id) {
        ExpenseDTO dto = expenseService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> insert(@Valid @RequestBody ExpenseDTO dto) {
        dto = expenseService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> update(@PathVariable Long id, @Valid @RequestBody ExpenseDTO dto) {
        dto = expenseService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
