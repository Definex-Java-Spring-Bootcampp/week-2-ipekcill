package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.controller.model.ApiResponse;
import com.patika.kredinbizdeservice.controller.model.ApplicationDto;
import com.patika.kredinbizdeservice.enums.LoanType;
import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.service.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final IApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<Application>> saveApplication(@RequestBody ApplicationDto applicationDto) {
        Application savedApplication = applicationService.saveApplication(applicationDto);
        ApiResponse<Application> response = new ApiResponse<>(savedApplication, true, "Application Created!");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Application>>> getAllApplications(@RequestParam(required = false) LoanType type) {
        List<Application> allApplications = applicationService.getAllApplications(type);
        ApiResponse<List<Application>> response = new ApiResponse<>(allApplications, true, "Applications Found by Given Type");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Application>> getApplicationById(@PathVariable Long id) {
        Application application = applicationService.getApplicationById(id);
        ApiResponse<Application> response = new ApiResponse<>(application, true, "Application Found by Given Id!");
        return ResponseEntity.ok(response);
    }
}
