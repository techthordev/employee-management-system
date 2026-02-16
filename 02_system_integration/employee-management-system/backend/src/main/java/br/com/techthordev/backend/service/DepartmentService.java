package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.DepartmentCreateRequest;
import br.com.techthordev.backend.dto.request.DepartmentUpdateRequest;
import br.com.techthordev.backend.dto.response.DepartmentResponse;
import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.mapper.DepartmentMapper;
import br.com.techthordev.backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentResponse create(DepartmentCreateRequest request) {
        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found " + id));
        return departmentMapper.toResponse(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public DepartmentResponse update(Long id, DepartmentUpdateRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));

        departmentMapper.updateEntity(request, department);
        Department updated = departmentRepository.save(department);
        return departmentMapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found: " + id);
        }
        departmentRepository.deleteById(id);
    }

}
