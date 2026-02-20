package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.DepartmentCreateRequest;
import br.com.techthordev.backend.dto.request.DepartmentUpdateRequest;
import br.com.techthordev.backend.dto.response.DepartmentResponse;

import java.util.List;


public interface DepartmentService {

    DepartmentResponse create(DepartmentCreateRequest request);

    DepartmentResponse findById(Long id);

    List<DepartmentResponse> findAll();

    DepartmentResponse update(Long id, DepartmentUpdateRequest request);

    void delete(Long id);

}
