package com.patika.kredinbizdeservice.controller.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private LocalDate dateTime;

    public ApiResponse(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.dateTime = LocalDate.now();
    }
}
