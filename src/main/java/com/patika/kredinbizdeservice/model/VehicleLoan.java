package com.patika.kredinbizdeservice.model;

import com.patika.kredinbizdeservice.enums.LoanType;
import com.patika.kredinbizdeservice.enums.VehicleStatusType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VehicleLoan extends Loan {
    private String title;
    private VehicleStatusType vehicleStatusType;
    private LoanType loanType = LoanType.VEHICLE_LOAN;

    public VehicleLoan(Long id, BigDecimal amount, Integer installment, Double interestRate, String title,
                       VehicleStatusType vehicleStatusType) {
        super(id, amount, installment, interestRate);
        this.title = title;
        this.vehicleStatusType = vehicleStatusType;
    }
}
