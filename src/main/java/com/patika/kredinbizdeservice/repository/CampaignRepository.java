package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.Campaign;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CampaignRepository {
    private final List<Campaign> campaigns = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Campaign save(Campaign campaign) {
        campaign.setId(idCounter.incrementAndGet());
        campaigns.add(campaign);
        return campaign;
    }

    public List<Campaign> getAll() {
        return campaigns;
    }


    public List<Campaign> findAllByOrderByStartingDateDesc() {
        campaigns.sort(Comparator.comparing(Campaign::getStartingDate).reversed());
        return campaigns;
    }

    public Optional<Campaign> findById(Long id) {
        return campaigns.stream()
                .filter(campaign -> campaign.getId().equals(id))
                .findFirst();
    }
}
