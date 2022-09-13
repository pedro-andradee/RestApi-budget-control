package com.pedro.andrade.finance.control.dto;

import com.pedro.andrade.finance.control.entities.Income;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

public class IncomeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @Positive(message = "Value must be positive")
    private Double amount;
    @NotNull(message = "Must be a valid date")
    private LocalDate date;

    public IncomeDTO() {
    }

    public IncomeDTO(Long id, String description, Double amount, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public IncomeDTO(Income entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.amount = entity.getAmount();
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
