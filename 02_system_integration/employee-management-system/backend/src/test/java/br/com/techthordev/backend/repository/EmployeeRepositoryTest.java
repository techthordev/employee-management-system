package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.base.BaseDomainTest;
import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for Employee (Level 1).
 * Inherits Level 0 factory methods from BaseDomainTest.
 */
@DisplayName("Employee Repository Tests (Level 1)")
public class EmployeeRepositoryTest extends BaseDomainTest {

    @Test
    @DisplayName("Should save employee when linked to a valid department")
    void shouldSaveEmployeeWithDepartment() {

        // Arrange
        Department department = createAndSaveDepartment("Engineering");
        department =  departmentRepository.save(department);

        // Act
        Employee saved = createAndSaveEmployee(
                "John", "Doe", "johnd@techthordev.com.br", department
        );

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Engineering", saved.getDepartment().getName());
    }

    @Test
    @DisplayName("Should fail when saving employee without a department (NOT NULL constraint)")
    void shouldFailWhenDepartmentIsMissing() {

        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johnd@techthordev.com.br");

        // Act & Assert
        assertThrows(
            DataIntegrityViolationException.class,
            () -> employeeRepository.saveAndFlush(employee)
        );
    }

    @Test
    @DisplayName("Should fail when department ID does not exist in database (FK constraint)")
    void shouldFailWhenDepartmentDoesNotExist() {

        // Arrange
        Department notRealDepartment = new Department();
        notRealDepartment.setId(999L); // not existing department

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johnd@techthordev.com.br");
        employee.setDepartment(notRealDepartment);

        // Act & Assert
        assertThrows(
            DataIntegrityViolationException.class,
            () -> employeeRepository.saveAndFlush(employee),
            "Should throw DataIntegrityViolationException when department doesn't exist"
        );
    }
}
