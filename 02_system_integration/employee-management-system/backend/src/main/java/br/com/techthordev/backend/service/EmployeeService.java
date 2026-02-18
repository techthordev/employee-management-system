package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.EmployeeCreateRequest;
import br.com.techthordev.backend.dto.request.EmployeeUpdateRequest;
import br.com.techthordev.backend.dto.response.EmployeeResponse;
import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.mapper.EmployeeMapper;
import br.com.techthordev.backend.repository.DepartmentRepository;
import br.com.techthordev.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeResponse create(EmployeeCreateRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found " + request.getDepartmentId()));

        Employee employee = employeeMapper.toEntity(request);
        employee.setDepartment(department);

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found " + id));
        return employeeMapper.toResponse(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    public EmployeeResponse update(Long id, EmployeeUpdateRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found " + id));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found " + request.getDepartmentId()));

        employeeMapper.updateEntity(request, employee);
        employee.setDepartment(department);

        Employee updated = employeeRepository.save(employee);

        return employeeMapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found " + id);
        }
        employeeRepository.deleteById(id);
    }
}

