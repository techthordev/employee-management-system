package br.com.techthordev.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techthordev.backend.dto.request.DepartmentCreateRequest;
import br.com.techthordev.backend.dto.request.DepartmentUpdateRequest;
import br.com.techthordev.backend.dto.response.DepartmentResponse;
import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.exception.ResourceNotFoundException;
import br.com.techthordev.backend.mapper.DepartmentMapper;
import br.com.techthordev.backend.repository.DepartmentRepository;
import br.com.techthordev.backend.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse create(DepartmentCreateRequest request) {
        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found " + id));
        return departmentMapper.toResponse(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentUpdateRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + id));

        departmentMapper.updateEntity(request, department);
        Department updated = departmentRepository.save(department);
        return departmentMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found: " + id);
        }
        departmentRepository.deleteById(id);
    }

}
