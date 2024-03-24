package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.controller.model.UserDto;
import com.patika.kredinbizdeservice.model.Application;

import java.util.List;

public interface IUserService {
    UserDto save(UserDto dto);

    List<UserDto> getAll();

    UserDto getByEmail(String email);

    UserDto update(Long id, UserDto userDto);

    UserDto getById(Long id);

    List<Application> getUserApplications(String email);


}
