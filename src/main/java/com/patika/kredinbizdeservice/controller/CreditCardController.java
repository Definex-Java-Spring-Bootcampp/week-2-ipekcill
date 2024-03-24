package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.controller.model.ApiResponse;
import com.patika.kredinbizdeservice.controller.model.CreditCardDto;
import com.patika.kredinbizdeservice.controller.model.IdRequestDto;
import com.patika.kredinbizdeservice.model.CreditCard;
import com.patika.kredinbizdeservice.service.ICreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CreditCardController {
    private final ICreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreditCardDto>> create(@RequestBody CreditCardDto dto) {
        CreditCardDto savedCreditCard = creditCardService.save(dto);
        ApiResponse<CreditCardDto> response = new ApiResponse<>(savedCreditCard, true, "Credit Card Created!");
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<CreditCardDto>>> getAll() {
        List<CreditCardDto> allCreditCards = creditCardService.getAll();
        ApiResponse<List<CreditCardDto>> response = new ApiResponse<>(allCreditCards, true, "Credit Cards Found!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreditCard>> getById(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.getById(id);

        ApiResponse<CreditCard> response = new ApiResponse<>(creditCard, true, "Credit Card Found By Given Id!");
        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}/campaigns")
    public ResponseEntity<ApiResponse<CreditCardDto>> assignCampaignsToCreditCard(@PathVariable Long id, @RequestBody IdRequestDto campaignIds) {
        CreditCard creditCard = creditCardService.getById(id);
        CreditCardDto updatedCreditCardDto = creditCardService.assignCampaignsToCreditCard(creditCard, campaignIds);
        ApiResponse<CreditCardDto> response = new ApiResponse<>(updatedCreditCardDto, true, "Campaigns Assigned The Given Credit Card!");
        return ResponseEntity.ok(response);
    }

}
