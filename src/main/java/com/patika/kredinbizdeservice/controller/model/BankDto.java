package com.patika.kredinbizdeservice.controller.model;

import com.patika.kredinbizdeservice.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class BankDto {
    private Long id;
    private String name;
    private List<CreditCardDto> creditCards;
    private List<Loan> loanList;
}
