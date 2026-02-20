package br.com.techthordev.backend.service;

import java.util.List;

import br.com.techthordev.backend.dto.request.EmployeeCreateRequest;
import br.com.techthordev.backend.dto.request.EmployeeUpdateRequest;
import br.com.techthordev.backend.dto.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse create(EmployeeCreateRequest request);

    EmployeeResponse findById(Long id);

    List<EmployeeResponse> findAll();

    EmployeeResponse update(Long id, EmployeeUpdateRequest request);

    void delete(Long id);
}

