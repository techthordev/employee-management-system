package br.com.techthordev.employee_management_system.service.impl;

import br.com.techthordev.employee_management_system.dto.EmployeeDTO;
import br.com.techthordev.employee_management_system.entity.Employee;
import br.com.techthordev.employee_management_system.exception.ResourceNotFoundException;
import br.com.techthordev.employee_management_system.repository.EmployeeRepository;
import br.com.techthordev.employee_management_system.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // constructor injection
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // map Entity -> DTO
    private EmployeeDTO mapToDto(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    // map DTO -> Entity
    private Employee mapToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        return employee;
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id " + id));
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee employee = mapToEntity(dto);
        Employee saved =  employeeRepository.save(employee);
        return mapToDto(saved);
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());

        Employee updated = employeeRepository.save(existing);
        return mapToDto(updated);
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id " + id));
        employeeRepository.delete(existing);
    }
}
