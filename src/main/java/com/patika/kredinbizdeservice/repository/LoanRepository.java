package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LoanRepository {
    private final List<Loan> loans = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Loan> getAll() {
        return loans;
    }

    public Optional<Loan> findById(Long id) {
        return loans.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst();

    }

    public Loan save(Loan loan) {
        loan.setId(idCounter.incrementAndGet());
        loans.add(loan);
        return loan;
    }
}
