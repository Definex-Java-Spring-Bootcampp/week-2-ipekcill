package com.patika.kredinbizdeservice.service.impl;

import com.patika.kredinbizdeservice.controller.model.ProductDto;
import com.patika.kredinbizdeservice.enums.LoanType;
import com.patika.kredinbizdeservice.exception.BusinessException;
import com.patika.kredinbizdeservice.model.*;
import com.patika.kredinbizdeservice.repository.LoanRepository;
import com.patika.kredinbizdeservice.service.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService implements ILoanService {

    private final LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.getAll();
    }

    @Override
    public Loan getLoanById(Long id) {
        Optional<Loan> productOpt = loanRepository.findById(id);
        return productOpt.orElseThrow(() -> new BusinessException("Product not found by given id"));
    }

    @Override
    public Loan saveLoan(ProductDto loan) {
        Loan loan1;
        if (loan.getType().equals(LoanType.CONSUMER_LOAN)) {
            loan1 = new ConsumerLoan(loan.getId(), loan.getAmount(), loan.getInstallment(), loan.getInterestRate(), loan.getTitle());
        } else if (loan.getType().equals(LoanType.HOUSE_LOAN)) {
            loan1 = new HouseLoan(loan.getId(), loan.getAmount(), loan.getInstallment(), loan.getInterestRate(), loan.getTitle());
        } else if (loan.getType().equals(LoanType.VEHICLE_LOAN)) {
            loan1 = new VehicleLoan(loan.getId(), loan.getAmount(), loan.getInstallment(), loan.getInterestRate(), loan.getTitle(), loan.getVehicleStatusType());
        } else {
            throw new BusinessException("Please check the loan type !");
        }
        loanRepository.save(loan1);
        return loan1;
    }
}

