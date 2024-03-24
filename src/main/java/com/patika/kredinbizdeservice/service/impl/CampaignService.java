package com.patika.kredinbizdeservice.service.impl;

import com.patika.kredinbizdeservice.controller.model.CampaignDto;
import com.patika.kredinbizdeservice.exception.BusinessException;
import com.patika.kredinbizdeservice.mapper.ModelMapper;
import com.patika.kredinbizdeservice.model.Campaign;
import com.patika.kredinbizdeservice.repository.CampaignRepository;
import com.patika.kredinbizdeservice.service.ICampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    private final CampaignRepository campaignRepository;
    private final ModelMapper modelMapper = ModelMapper.INSTANCE;
    @Override
    public CampaignDto save(CampaignDto campaignDto) {
        Campaign campaign = campaignRepository.save(modelMapper.toCampaign(campaignDto));
        return modelMapper.toCampaignDto(campaign);
    }
    @Override
    public List<CampaignDto> getAll() {
        return modelMapper.toCampaigntoList(campaignRepository.getAll());
    }

    @Override
    public List<CampaignDto> getAllOrderByDate() {
        return modelMapper.toCampaigntoList(campaignRepository.findAllByOrderByStartingDateDesc());
    }

    @Override
    public CampaignDto getById(Long id) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(id);
        Campaign campaign = campaignOpt.orElseThrow(() -> new BusinessException("Campaign nor found by given id"));
        return modelMapper.toCampaignDto(campaign);
    }
}
