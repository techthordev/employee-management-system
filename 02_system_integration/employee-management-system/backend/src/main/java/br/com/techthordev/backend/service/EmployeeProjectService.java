package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.EmployeeProjectCreateRequest;
import br.com.techthordev.backend.dto.response.EmployeeProjectResponse;

import java.util.List;


public interface EmployeeProjectService {

    EmployeeProjectResponse assignProject(Long employeeId, EmployeeProjectCreateRequest request);

    List<EmployeeProjectResponse> findProjectsByEmployee(Long employeeId);

    void removeProject(Long employeeId, Long projectId);
    
}
