package com.patika.kredinbizdeservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationDto {
    private Long id;
    private LocalDateTime localDateTime;
    private ProductDto product;
    private UserDto user;
}
