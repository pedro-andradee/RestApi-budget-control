package com.pedro.andrade.finance.control.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedro.andrade.finance.control.enums.Category;

import java.io.Serializable;

public class ExpenseByCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Category category;
    @JsonProperty("total_amount")
    private Double totalAmount;

    public ExpenseByCategoryDTO(){
    }

    public ExpenseByCategoryDTO(Category category, Double totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
