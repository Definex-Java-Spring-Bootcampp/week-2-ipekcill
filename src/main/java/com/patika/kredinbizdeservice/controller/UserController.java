package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.controller.model.ApiResponse;
import com.patika.kredinbizdeservice.controller.model.UserDto;
import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(@RequestBody UserDto dto) {
        UserDto savedUser = userService.save(dto);
        ApiResponse<UserDto> response = new ApiResponse<>(savedUser, true, "User Created!");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAll() {
        List<UserDto> allUsers = userService.getAll();
        ApiResponse<List<UserDto>> response = new ApiResponse<>(allUsers, true, "Users Found!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserDto>> getByEmail(@PathVariable String email) {
        UserDto userDto = userService.getByEmail(email);
        ApiResponse<UserDto> response = new ApiResponse<>(userDto, true, "User Found!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getById(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        ApiResponse<UserDto> response = new ApiResponse<>(userDto, true, "User Found!");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<List<Application>>> getUserApplicationsByEmail(@RequestParam String email) {
        List<Application> applicationList = userService.getUserApplications(email);
        ApiResponse<List<Application>> response = new ApiResponse<>(applicationList, true, "Applications Found For User By Given Email!");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(id, userDto);
        ApiResponse<UserDto> response = new ApiResponse<>(updatedUser, true, "User Updated!");
        return ResponseEntity.ok(response);
    }
}
