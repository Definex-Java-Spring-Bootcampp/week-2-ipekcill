package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.controller.model.ProductDto;
import com.patika.kredinbizdeservice.model.Loan;

import java.util.List;

public interface ILoanService {
    List<Loan> getAllLoans();

    Loan getLoanById(Long id);

    Loan saveLoan(ProductDto loan);
}
