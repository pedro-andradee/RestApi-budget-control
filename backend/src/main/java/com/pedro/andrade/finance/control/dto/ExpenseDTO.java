package com.pedro.andrade.finance.control.dto;

import com.pedro.andrade.finance.control.entities.Expense;
import com.pedro.andrade.finance.control.enums.Category;

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
    private Category category;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Long id, String description, Double value, LocalDate date, Category category) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
        this.category = category;
    }

    public ExpenseDTO(Expense entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.value = entity.getValue();
        this.date = entity.getDate();
        this.category = entity.getCategory();
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
