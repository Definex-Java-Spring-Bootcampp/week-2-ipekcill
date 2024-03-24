package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.Bank;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BankRepository {
    private final AtomicLong idCounter = new AtomicLong();
    private final List<Bank> banks = new ArrayList<>();

    public Bank save(Bank bank) {
        bank.setId(idCounter.incrementAndGet());
        banks.add(bank);
        return bank;
    }

    public List<Bank> getAll() {

        return banks;
    }

    public Optional<Bank> findById(Long id) {
        return banks.stream()
                .filter(bank -> bank.getId().equals(id))
                .findFirst();
    }
}
