package br.com.techthordev.backend.controller;

import br.com.techthordev.backend.config.ApiVersionController;
import br.com.techthordev.backend.dto.request.*;
import br.com.techthordev.backend.dto.response.EmployeeProfileResponse;
import br.com.techthordev.backend.dto.response.EmployeeProjectResponse;
import br.com.techthordev.backend.dto.response.EmployeeResponse;
import br.com.techthordev.backend.service.EmployeeProfileService;
import br.com.techthordev.backend.service.EmployeeProjectService;
import br.com.techthordev.backend.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiVersionController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Employee management APIs")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeProfileService employeeProfileService;
    private final EmployeeProjectService employeeProjectService;

    // Employee Endpoints

    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee and assigns them to a department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeCreateRequest request) {
        EmployeeResponse response = employeeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Retrieves a specific employee with their department information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get all employees",
            description = "Retrieves a list of all employees with their department information")
    @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        List<EmployeeResponse> response = employeeService.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update employee",
            description = "Updates an existing employee's information including department assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee or department not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        EmployeeResponse response = employeeService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete employee",
            description = "Deletes an employee. This will cascade to their profile and project assignments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // EmployeeProfile Endpoints (nested)

    @Operation(
            summary = "Create employee profile",
            description = "Creates a profile for an existing employee with bio and skills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Profile already exists for this employee", content = @Content)
    })
    @PostMapping("/{employeeId}/profile")
    public ResponseEntity<EmployeeProfileResponse> createProfile(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeProfileCreateRequest request) {
        EmployeeProfileResponse response = employeeProfileService.create(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get employee profile",
            description = "Retrieves the profile of a specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found for this employee", content = @Content)
    })
    @GetMapping("/{employeeId}/profile")
    public ResponseEntity<EmployeeProfileResponse> getProfile(@PathVariable Long employeeId) {
        EmployeeProfileResponse response = employeeProfileService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update employee profile",
            description = "Updates the profile information of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content)
    })
    @PutMapping("/{employeeId}/profile")
    public ResponseEntity<EmployeeProfileResponse> updateProfile(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeProfileUpdateRequest request) {
        EmployeeProfileResponse response = employeeProfileService.update(employeeId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete employee profile",
            description = "Deletes the profile of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content)
    })
    @DeleteMapping("/{employeeId}/profile")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long employeeId) {
        employeeProfileService.delete(employeeId);
        return ResponseEntity.noContent().build();
    }

    // EmployeeProject Endpoints (NEW!)

    @Operation(
            summary = "Assign project to employee",
            description = "Creates an assignment between an employee and a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee or project not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already assigned to this project", content = @Content)
    })
    @PostMapping("/{employeeId}/projects")
    public ResponseEntity<EmployeeProjectResponse> assignProject(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeProjectCreateRequest request) {
        EmployeeProjectResponse response = employeeProjectService.assignProject(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get employee's projects",
            description = "Retrieves all projects assigned to a specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping("/{employeeId}/projects")
    public ResponseEntity<List<EmployeeProjectResponse>> getProjects(@PathVariable Long employeeId) {
        List<EmployeeProjectResponse> responses = employeeProjectService.findProjectsByEmployee(employeeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Remove project from employee",
            description = "Removes the assignment between an employee and a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project removed successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content)
    })
    @DeleteMapping("/{employeeId}/projects/{projectId}")
    public ResponseEntity<Void> removeProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        employeeProjectService.removeProject(employeeId, projectId);
        return ResponseEntity.noContent().build();
    }
}
