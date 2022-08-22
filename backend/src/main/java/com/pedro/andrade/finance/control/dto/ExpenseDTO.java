package com.pedro.andrade.finance.control.dto;

import com.pedro.andrade.finance.control.entities.Expense;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

public class ExpenseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @Positive(message = "Value must be positive")
    private Double value;
    @NotNull(message = "Must be a valid date")
    private LocalDate date;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Long id, String description, Double value, LocalDate date) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
    }

    public ExpenseDTO(Expense entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.value = entity.getValue();
        this.date = entity.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
