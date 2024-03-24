package com.patika.kredinbizdeservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class Loan implements Product {
    private Long id;
    private BigDecimal amount;
    private Integer installment;
    private Double interestRate;
    private Bank bank;

    public Loan(Long id,BigDecimal amount, Integer installment, Double interestRate) {
        this.id= id;
        this.amount = amount;
        this.installment = installment;
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "amount=" + amount +
                ", installment=" + installment +
                ", bank=" + bank +
                ", interestRate=" + interestRate +
                '}';
    }
}
