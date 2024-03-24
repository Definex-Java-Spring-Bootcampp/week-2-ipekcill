package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.controller.model.ApplicationDto;
import com.patika.kredinbizdeservice.enums.LoanType;
import com.patika.kredinbizdeservice.model.Application;

import java.util.List;

public interface IApplicationService {
    List<Application> getAllApplications(LoanType loanType);

    Application getApplicationById(Long id);

    Application saveApplication(ApplicationDto applicationDto);
}
