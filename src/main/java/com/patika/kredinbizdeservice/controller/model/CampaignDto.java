package com.patika.kredinbizdeservice.controller.model;

import com.patika.kredinbizdeservice.enums.SectorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CampaignDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate dueDate;
    private LocalDate startingDate;
    private SectorType sectorType;
}
