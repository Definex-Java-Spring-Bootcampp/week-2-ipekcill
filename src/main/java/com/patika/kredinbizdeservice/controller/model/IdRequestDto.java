package com.patika.kredinbizdeservice.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class IdRequestDto {
    private List<Long> ids;
}
