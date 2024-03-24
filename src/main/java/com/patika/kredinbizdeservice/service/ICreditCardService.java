package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.controller.model.CreditCardDto;
import com.patika.kredinbizdeservice.controller.model.IdRequestDto;
import com.patika.kredinbizdeservice.model.CreditCard;

import java.util.List;

public interface ICreditCardService {
    CreditCardDto save(CreditCardDto dto);

    List<CreditCardDto> getAll();

    CreditCard getById(Long id);

    CreditCardDto assignCampaignsToCreditCard(CreditCard creditCard, IdRequestDto request);
}
