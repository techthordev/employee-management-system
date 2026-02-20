package br.com.techthordev.backend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techthordev.backend.dto.request.EmployeeProfileCreateRequest;
import br.com.techthordev.backend.dto.request.EmployeeProfileUpdateRequest;
import br.com.techthordev.backend.dto.response.EmployeeProfileResponse;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.entity.EmployeeProfile;
import br.com.techthordev.backend.exception.ResourceNotFoundException;
import br.com.techthordev.backend.mapper.EmployeeProfileMapper;
import br.com.techthordev.backend.repository.EmployeeProfileRepository;
import br.com.techthordev.backend.repository.EmployeeRepository;
import br.com.techthordev.backend.service.EmployeeProfileService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeProfileMapper employeeProfileMapper;

    @Override
    public EmployeeProfileResponse create(Long employeeId, EmployeeProfileCreateRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        if (employeeProfileRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Profile already exists for employee: " + employeeId);
        }

        EmployeeProfile profile = employeeProfileMapper.toEntity(request);
        profile.setId(employeeId);  // Shared Primary Key!
        profile.setEmployee(employee);

        EmployeeProfile saved = employeeProfileRepository.save(profile);
        return employeeProfileMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeProfileResponse findByEmployeeId(Long employeeId) {
        EmployeeProfile profile = employeeProfileRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for employee: " + employeeId));
        return employeeProfileMapper.toResponse(profile);
    }

    @Override
    public EmployeeProfileResponse update(Long employeeId, EmployeeProfileUpdateRequest request) {
        EmployeeProfile profile = employeeProfileRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for employee: " + employeeId));

        employeeProfileMapper.updateEntity(request, profile);

        EmployeeProfile updated = employeeProfileRepository.save(profile);
        return employeeProfileMapper.toResponse(updated);
    }

    @Override
    public void delete(Long employeeId) {
        if (!employeeProfileRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Profile not found for employee: " + employeeId);
        }
        employeeProfileRepository.deleteById(employeeId);
    }

}
