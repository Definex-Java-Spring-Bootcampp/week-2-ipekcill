package com.patika.kredinbizdeservice.model;

import com.patika.kredinbizdeservice.enums.LoanType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreditCard implements Product {
    private Long id;
    private String name;
    private BigDecimal fee;
    private BigDecimal limit;
    private List<Campaign> campaigns;
    private Bank bank;
    private final LoanType loanType = LoanType.CREDIT_CARD;

    public CreditCard(Long id, String name, BigDecimal fee, BigDecimal limit, Bank bank) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.limit = limit;
        this.campaigns = new ArrayList<>();
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "fee=" + fee +
                ", limit=" + limit +
                ", campaigns=" + campaigns +
                ", loanType=" + loanType +
                '}';
    }
}
