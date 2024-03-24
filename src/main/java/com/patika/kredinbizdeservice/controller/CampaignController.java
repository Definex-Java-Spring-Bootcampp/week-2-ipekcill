package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.controller.model.ApiResponse;
import com.patika.kredinbizdeservice.controller.model.CampaignDto;
import com.patika.kredinbizdeservice.service.ICampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final ICampaignService campaignService;

    @PostMapping
    public ResponseEntity<ApiResponse<CampaignDto>> create(@RequestBody CampaignDto dto) {
        CampaignDto savedCampaign = campaignService.save(dto);
        ApiResponse<CampaignDto> response = new ApiResponse<>(savedCampaign, true, "Campaign Created");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignDto>>> getAll() {
        List<CampaignDto> allCampaigns = campaignService.getAll();
        ApiResponse<List<CampaignDto>> response = new ApiResponse<>(allCampaigns, true, "Campaigns Found");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignDto>> getById(@PathVariable Long id) {
        CampaignDto campaign = campaignService.getById(id);
        ApiResponse<CampaignDto> response = new ApiResponse<>(campaign, true, "Campaign Found By Given Id");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<List<CampaignDto>>> getAllCampaignsOrderByDate() {
        List<CampaignDto> campaigns = campaignService.getAllOrderByDate();
        ApiResponse<List<CampaignDto>> response = new ApiResponse<>(campaigns, true, "Campaigns Ordered");
        return ResponseEntity.ok(response);
    }
}
