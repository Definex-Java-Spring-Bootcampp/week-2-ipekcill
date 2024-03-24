package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.controller.model.ApiResponse;
import com.patika.kredinbizdeservice.controller.model.ProductDto;
import com.patika.kredinbizdeservice.model.Loan;
import com.patika.kredinbizdeservice.service.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final ILoanService loanService;

    @PostMapping
    public ResponseEntity<ApiResponse<Loan>> createLoan(@RequestBody ProductDto loan) {
        Loan createdLoan = loanService.saveLoan(loan);
        ApiResponse<Loan> response = new ApiResponse<>(createdLoan, true, "Loan Created!");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Loan>>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        ApiResponse<List<Loan>> response = new ApiResponse<>(loans, true, "Loans Found");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Loan>> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        ApiResponse<Loan> response = new ApiResponse<>(loan, true, "Loan Found By Given Id!");
        return ResponseEntity.ok(response);
    }
}

