package com.patika.kredinbizdeservice.controller.model;

import com.patika.kredinbizdeservice.enums.LoanType;
import com.patika.kredinbizdeservice.enums.VehicleStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private LoanType type;
    private String title;
    private BigDecimal amount;
    private Integer installment;
    private Double interestRate;
    private VehicleStatusType vehicleStatusType;
}
