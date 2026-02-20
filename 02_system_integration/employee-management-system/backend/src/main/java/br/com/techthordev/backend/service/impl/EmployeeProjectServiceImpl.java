package br.com.techthordev.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techthordev.backend.dto.request.EmployeeProjectCreateRequest;
import br.com.techthordev.backend.dto.response.EmployeeProjectResponse;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.entity.EmployeeProject;
import br.com.techthordev.backend.entity.EmployeeProjectId;
import br.com.techthordev.backend.entity.Project;
import br.com.techthordev.backend.exception.ResourceAlreadyExistsException;
import br.com.techthordev.backend.exception.ResourceNotFoundException;
import br.com.techthordev.backend.mapper.EmployeeProjectMapper;
import br.com.techthordev.backend.repository.EmployeeProjectRepository;
import br.com.techthordev.backend.repository.EmployeeRepository;
import br.com.techthordev.backend.repository.ProjectRepository;
import br.com.techthordev.backend.service.EmployeeProjectService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectMapper employeeProjectMapper;

    @Override
    public EmployeeProjectResponse assignProject(Long employeeId, EmployeeProjectCreateRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found" + employeeId));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + request.getProjectId()));

        EmployeeProjectId id = new EmployeeProjectId(employeeId, request.getProjectId());

        if (employeeProjectRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Employee already assigned to this project");
        }

        EmployeeProject assignment = new EmployeeProject();
        assignment.setId(id);
        assignment.setEmployee(employee);
        assignment.setProject(project);

        EmployeeProject saved = employeeProjectRepository.save(assignment);
        return employeeProjectMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeProjectResponse> findProjectsByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found: " + employeeId);
        }

        return employeeProjectRepository.findByEmployee_Id(employeeId)
                .stream()
                .map(employeeProjectMapper::toResponse)
                .toList();
    }

    @Override
    public void removeProject(Long employeeId, Long projectId) {
        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);

        if (!employeeProjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assignment not found for employee " + employeeId + " and project " + projectId);
        }

        employeeProjectRepository.deleteById(id);
    }
    
}
