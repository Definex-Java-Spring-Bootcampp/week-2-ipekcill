package com.patika.kredinbizdeservice.service.impl;

import com.patika.kredinbizdeservice.controller.model.UserDto;
import com.patika.kredinbizdeservice.exception.BusinessException;
import com.patika.kredinbizdeservice.mapper.ModelMapper;
import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.repository.ApplicationRepository;
import com.patika.kredinbizdeservice.repository.UserRepository;
import com.patika.kredinbizdeservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper = ModelMapper.INSTANCE;

    @Override
    public UserDto save(UserDto dto) {
        User user = userRepository.save(modelMapper.toUser(dto));
        return modelMapper.toUserDto(user);

    }

    @Override
    public List<UserDto> getAll() {
        return modelMapper.toUserDtoList(userRepository.getAll());
    }

    @Override
    public UserDto getByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseThrow(() -> new BusinessException("User not found by given email"));
        return modelMapper.toUserDto(user);
    }
    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = modelMapper.toUser(userDto);
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User existUser = userOpt.get();
            existUser.setName(user.getName());
            existUser.setSurname(user.getSurname());
            existUser.setEmail(user.getEmail());
            existUser.setPassword(user.getPassword());
            existUser.setIsActive(user.getIsActive());
            existUser.setPhoneNumber(user.getPhoneNumber());
            return modelMapper.toUserDto(userOpt.get());
        } else {
            throw new BusinessException("User not found by given email");
        }
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(() -> new BusinessException("User not found by given id"));
        return modelMapper.toUserDto(user);
    }

    @Override
    public List<Application> getUserApplications(String email) {
        List<Application> userApplications = new ArrayList<>();
        List<Application> allApplications = applicationRepository.getAll();
        for (Application application : allApplications) {
            if (application.getUser().getEmail().equals(email)) {
                userApplications.add(application);
            }
        }
        return userApplications;

    }
}
