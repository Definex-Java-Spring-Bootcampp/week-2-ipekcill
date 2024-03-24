package com.patika.kredinbizdeservice.service.impl;

import com.patika.kredinbizdeservice.controller.model.ApplicationDto;
import com.patika.kredinbizdeservice.controller.model.UserDto;
import com.patika.kredinbizdeservice.enums.LoanType;
import com.patika.kredinbizdeservice.exception.BusinessException;
import com.patika.kredinbizdeservice.mapper.ModelMapper;
import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.model.CreditCard;
import com.patika.kredinbizdeservice.model.Product;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.repository.ApplicationRepository;
import com.patika.kredinbizdeservice.service.IApplicationService;
import com.patika.kredinbizdeservice.service.ICreditCardService;
import com.patika.kredinbizdeservice.service.ILoanService;
import com.patika.kredinbizdeservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService implements IApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ICreditCardService creditCardService;
    private final ILoanService loanService;
    private final IUserService userService;
    private final ModelMapper modelMapper = ModelMapper.INSTANCE;

    @Override
    public Application saveApplication(ApplicationDto applicationDto) {
        UserDto userDto = userService.getById(applicationDto.getUser().getId());
        User user = modelMapper.toUser(userDto);

        if (applicationDto.getProduct().getType().equals(LoanType.CREDIT_CARD)) {
            CreditCard creditCard = creditCardService.getById(applicationDto.getProduct().getId());
            Application savedApplication1 = new Application(applicationDto.getId(), applicationDto.getLocalDateTime(), creditCard, user);
            return applicationRepository.save(savedApplication1);

        } else {
            Product loan = loanService.getLoanById(applicationDto.getProduct().getId());
            Application savedApplication2 = new Application(applicationDto.getId(), applicationDto.getLocalDateTime(), loan, user);
            return applicationRepository.save(savedApplication2);
        }
    }

    @Override
    public List<Application> getAllApplications(LoanType type) {
        if (type == null) {
            return applicationRepository.getAll();
        }
        return applicationRepository.getAll().stream().filter(f -> f.getProduct().getLoanType().equals(type)).collect(Collectors.toList());

    }

    @Override
    public Application getApplicationById(Long id) {
        Optional<Application> applicationOpt = applicationRepository.findById(id);
        return applicationOpt.orElseThrow(() -> new BusinessException("Application not found by given id"));
    }
}
