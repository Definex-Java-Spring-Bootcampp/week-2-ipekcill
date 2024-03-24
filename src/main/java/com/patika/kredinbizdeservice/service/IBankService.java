package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.controller.model.BankDto;
import com.patika.kredinbizdeservice.controller.model.IdRequestDto;
import com.patika.kredinbizdeservice.model.Bank;

import java.util.List;

public interface IBankService {
    BankDto saveBank(BankDto dto);

    List<BankDto> getAllBanks();

    Bank assignCreditCardsToBank(Bank bank, IdRequestDto request);

    Bank getBankById(Long id);
}
