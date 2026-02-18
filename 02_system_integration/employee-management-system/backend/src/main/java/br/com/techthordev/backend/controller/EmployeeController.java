package br.com.techthordev.backend.controller;

import br.com.techthordev.backend.dto.request.*;
import br.com.techthordev.backend.dto.response.EmployeeProfileResponse;
import br.com.techthordev.backend.dto.response.EmployeeProjectResponse;
import br.com.techthordev.backend.dto.response.EmployeeResponse;
import br.com.techthordev.backend.service.EmployeeProfileService;
import br.com.techthordev.backend.service.EmployeeProjectService;
import br.com.techthordev.backend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeProfileService employeeProfileService;
    private final EmployeeProjectService employeeProjectService;

    // Employee Endpoints

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeCreateRequest request) {
        EmployeeResponse response = employeeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        List<EmployeeResponse> response = employeeService.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        EmployeeResponse response = employeeService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // EmployeeProfile Endpoints (nested)

    @PostMapping("/{employeeId}/profile")
    public ResponseEntity<EmployeeProfileResponse> createProfile(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeProfileCreateRequest request) {
        EmployeeProfileResponse response = employeeProfileService.create(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{employeeId}/profile")
    public ResponseEntity<EmployeeProfileResponse> getProfile(@PathVariable Long employeeId) {
        EmployeeProfileResponse response = employeeProfileService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{employeeId}/profile")
    public ResponseEntity<EmployeeProfileResponse> updateProfile(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeProfileUpdateRequest request) {
        EmployeeProfileResponse response = employeeProfileService.update(employeeId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{employeeId}/profile")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long employeeId) {
        employeeProfileService.delete(employeeId);
        return ResponseEntity.noContent().build();
    }

    // EmployeeProject Endpoints (NEW!)

    @PostMapping("/{employeeId}/projects")
    public ResponseEntity<EmployeeProjectResponse> assignProject(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeProjectCreateRequest request) {
        EmployeeProjectResponse response = employeeProjectService.assignProject(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{employeeId}/projects")
    public ResponseEntity<List<EmployeeProjectResponse>> getProjects(@PathVariable Long employeeId) {
        List<EmployeeProjectResponse> responses = employeeProjectService.findProjectsByEmployee(employeeId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{employeeId}/projects/{projectId}")
    public ResponseEntity<Void> removeProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        employeeProjectService.removeProject(employeeId, projectId);
        return ResponseEntity.noContent().build();
    }
}
