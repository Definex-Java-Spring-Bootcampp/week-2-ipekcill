package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CreditCardRepository {
    private final AtomicLong idCounter = new AtomicLong();
    private final List<CreditCard> creditCards = new ArrayList<>();

    public CreditCard save(CreditCard creditCard) {
        creditCard.setId(idCounter.incrementAndGet());
        creditCards.add(creditCard);
        return creditCard;
    }

    public List<CreditCard> getAll() {
        return creditCards;
    }

    public Optional<CreditCard> findById(Long id) {
        return creditCards.stream()
                .filter(creditCard -> creditCard.getId().equals(id))
                .findFirst();

    }
}

