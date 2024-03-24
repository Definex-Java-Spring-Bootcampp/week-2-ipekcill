package com.patika.kredinbizdeservice.model;

import com.patika.kredinbizdeservice.enums.LoanType;

public interface Product {
    LoanType getLoanType();
    Long getId();

}
