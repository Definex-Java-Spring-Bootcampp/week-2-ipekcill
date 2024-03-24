package com.patika.kredinbizdeservice.model;

import com.patika.kredinbizdeservice.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Application {
    private Long id;
    private LocalDateTime localDateTime;
    private ApplicationStatus applicationStatus;
    private Product product;
    private User user;

    public Application(Long id, LocalDateTime localDateTime, Product product, User user) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.product = product;
        this.user = user;
        this.applicationStatus = ApplicationStatus.INITIAL;
    }
}
