package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.controller.model.CampaignDto;

import java.util.List;

public interface ICampaignService {
    CampaignDto save(CampaignDto campaignDto);

    List<CampaignDto> getAll();

    List<CampaignDto> getAllOrderByDate();

    CampaignDto getById(Long id);
}
