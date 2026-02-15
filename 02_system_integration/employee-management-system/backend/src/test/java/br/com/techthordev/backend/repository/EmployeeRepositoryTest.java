package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void shouldSaveEmployeeWhithDepartment() {

        Department department = new Department();
        department.setName("Engineering");
        department =  departmentRepository.save(department);

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johnd@techthordev.com.br");
        employee.setDepartment(department);

        Employee saved = employeeRepository.save(employee);

        assertNotNull(saved.getId());
        assertEquals("Engineering", saved.getDepartment().getName());

    }

    @Test
    void shouldFailWhenDepartmentIsMissing() {

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johnd@techthordev.com.br");

        assertThrows(Exception.class, () -> {
            employeeRepository.saveAndFlush(employee);
        });
    }

    @Test
    void shouldFailWhenDepartmentDoesNotExist() {

        Department notRealDepartment = new Department();
        notRealDepartment.setId(999L); // not existing department

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johnd@techthordev.com.br");
        employee.setDepartment(notRealDepartment);

        assertThrows(
            DataIntegrityViolationException.class,
            () -> employeeRepository.saveAndFlush(employee),
            "Should throw DataIntegrityViolationException when department doesn't exist"
        );
    }
}
