package br.com.techthordev.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.techthordev.backend.dto.request.EmployeeCreateRequest;
import br.com.techthordev.backend.dto.request.EmployeeUpdateRequest;
import br.com.techthordev.backend.dto.response.EmployeeResponse;
import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.exception.ResourceNotFoundException;
import br.com.techthordev.backend.mapper.EmployeeMapper;
import br.com.techthordev.backend.repository.DepartmentRepository;
import br.com.techthordev.backend.repository.EmployeeRepository;
import br.com.techthordev.backend.service.EmployeeService;
import org.springframework.transaction.annotation.Transactional; 
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse create(EmployeeCreateRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found " + request.getDepartmentId()));

        Employee employee = employeeMapper.toEntity(request);
        employee.setDepartment(department);

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found " + id));
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeUpdateRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found " + id));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found " + request.getDepartmentId()));

        employeeMapper.updateEntity(request, employee);
        employee.setDepartment(department);

        Employee updated = employeeRepository.save(employee);

        return employeeMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found " + id);
        }
        employeeRepository.deleteById(id);
    }

}
