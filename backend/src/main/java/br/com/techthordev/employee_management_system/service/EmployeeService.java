package br.com.techthordev.employee_management_system.service;

import br.com.techthordev.employee_management_system.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {

    Page<EmployeeDTO> getAllEmployees(Pageable pageable);

    Page<EmployeeDTO> searchEmployees(String searchTerm, Pageable pageable);

    EmployeeDTO getEmployeeById(Integer id);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);

    void deleteEmployee(Integer id);

}
