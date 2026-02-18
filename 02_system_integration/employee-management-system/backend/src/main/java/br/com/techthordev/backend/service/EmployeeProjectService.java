package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.EmployeeProjectCreateRequest;
import br.com.techthordev.backend.dto.response.EmployeeProjectResponse;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.entity.EmployeeProject;
import br.com.techthordev.backend.entity.EmployeeProjectId;
import br.com.techthordev.backend.entity.Project;
import br.com.techthordev.backend.mapper.EmployeeProjectMapper;
import br.com.techthordev.backend.repository.EmployeeProjectRepository;
import br.com.techthordev.backend.repository.EmployeeRepository;
import br.com.techthordev.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectMapper employeeProjectMapper;

    public EmployeeProjectResponse assignProject(Long employeeId, EmployeeProjectCreateRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found" + employeeId));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found: " + request.getProjectId()));

        EmployeeProjectId id = new EmployeeProjectId(employeeId, request.getProjectId());

        if (employeeProjectRepository.existsById(id)) {
            throw new RuntimeException("Employee already assigned to this project");
        }

        EmployeeProject assignment = new EmployeeProject();
        assignment.setId(id);
        assignment.setEmployee(employee);
        assignment.setProject(project);

        EmployeeProject saved = employeeProjectRepository.save(assignment);
        return employeeProjectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<EmployeeProjectResponse> findProjectsByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found: " + employeeId);
        }

        return employeeProjectRepository.findByEmployee_Id(employeeId)
                .stream()
                .map(employeeProjectMapper::toResponse)
                .toList();
    }

    public void removeProject(Long employeeId, Long projectId) {
        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);

        if (!employeeProjectRepository.existsById(id)) {
            throw new RuntimeException("Assignment not found for employee " + employeeId + " and project " + projectId);
        }

        employeeProjectRepository.deleteById(id);
    }
}
