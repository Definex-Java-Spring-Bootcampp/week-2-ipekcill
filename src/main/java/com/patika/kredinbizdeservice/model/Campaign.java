package com.patika.kredinbizdeservice.model;


import com.patika.kredinbizdeservice.enums.SectorType;
import lombok.Data;

import java.time.LocalDate;


@Data
public class Campaign {
    private Long id;
    private String title;
    private String content;
    private LocalDate dueDate;
    private LocalDate startingDate;
    private SectorType sectorType;

    public Campaign(Long id, String title, String content, LocalDate dueDate, LocalDate startingDate, SectorType sectorType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.startingDate = startingDate;
        this.sectorType = sectorType;
    }
}
