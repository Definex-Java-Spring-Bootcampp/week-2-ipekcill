package com.patika.kredinbizdeservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bank {
    private Long id;
    private String name;
    private List<Loan> loanList;
    private List<CreditCard> creditCards;

    public Bank(Long id, String name) {
        this.id = id;
        this.name = name;
        this.loanList = new ArrayList<>();
        this.creditCards = new ArrayList<>();
    }
}
