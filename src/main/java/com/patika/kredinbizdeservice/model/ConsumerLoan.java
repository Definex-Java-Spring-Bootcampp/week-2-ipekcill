package com.patika.kredinbizdeservice.model;

import com.patika.kredinbizdeservice.enums.LoanType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsumerLoan extends Loan {
    private String title;
    private LoanType loanType = LoanType.CONSUMER_LOAN;

    public ConsumerLoan(Long id, BigDecimal amount, Integer installment, Double interestRate, String title) {
        super(id, amount, installment, interestRate);
        this.title = title;
    }
}
