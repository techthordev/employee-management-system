package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.EmployeeProfileCreateRequest;
import br.com.techthordev.backend.dto.request.EmployeeProfileUpdateRequest;
import br.com.techthordev.backend.dto.response.EmployeeProfileResponse;


public interface EmployeeProfileService {

    EmployeeProfileResponse create(Long employeeId, EmployeeProfileCreateRequest request);

    EmployeeProfileResponse findByEmployeeId(Long employeeId);

    EmployeeProfileResponse update(Long employeeId, EmployeeProfileUpdateRequest request);

    void delete(Long employeeId);

}