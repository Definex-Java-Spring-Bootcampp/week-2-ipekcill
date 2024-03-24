package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.controller.model.*;
import com.patika.kredinbizdeservice.mapper.ModelMapper;
import com.patika.kredinbizdeservice.model.Bank;
import com.patika.kredinbizdeservice.model.CreditCard;
import com.patika.kredinbizdeservice.service.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/banks")
@RequiredArgsConstructor
public class BankController {
    private final IBankService bankService;
    private final ModelMapper modelMapper = ModelMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<ApiResponse<BankDto>> create(@RequestBody BankDto dto) {
        BankDto savedBank = bankService.saveBank(dto);
        ApiResponse<BankDto> response = new ApiResponse<>(savedBank, true, "Bank Created!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Bank>> getBankById(@PathVariable Long id) {
        Bank bank = bankService.getBankById(id);
        ApiResponse<Bank> response = new ApiResponse<>(bank, true, "Bank Found By Given Id");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BankDto>>> getAllBanks() {
        List<BankDto> allBanks = bankService.getAllBanks();
        ApiResponse<List<BankDto>> response = new ApiResponse<>(allBanks, true, "Bank Found");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<ApiResponse<List<CreditCardDto>>> getCreditCards(@PathVariable Long id) {
        Bank bank = bankService.getBankById(id);
        List<CreditCard> creditCards = bank.getCreditCards();
        List<CreditCardDto> creditCardsDto = modelMapper.toCreditCardDtoList(creditCards);
        ApiResponse<List<CreditCardDto>> response = new ApiResponse<>(creditCardsDto, true, "Credit Cards of Bank Found");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cards")
    public ResponseEntity<ApiResponse<Bank>> assignCreditCardsToBank(@PathVariable Long id, @RequestBody IdRequestDto request) {
        Bank bank = bankService.getBankById(id);
        Bank updatedBank = bankService.assignCreditCardsToBank(bank, request);
        ApiResponse<Bank> response = new ApiResponse<>(updatedBank, true, "Credit Cards Assigned to Bank");
        return ResponseEntity.ok(response);
    }
}

