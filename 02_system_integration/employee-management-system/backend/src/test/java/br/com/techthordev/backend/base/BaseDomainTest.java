package br.com.techthordev.backend.base;

import br.com.techthordev.backend.entity.Department;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.entity.Project;
import br.com.techthordev.backend.repository.DepartmentRepository;
import br.com.techthordev.backend.repository.EmployeeProjectRepository;
import br.com.techthordev.backend.repository.EmployeeRepository;
import br.com.techthordev.backend.repository.ProjectRepository;
import br.com.techthordev.backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for tests requiring domain entities (Level 0, 1, 2).
 * Provides helper methods to satisfy foreign key constraints.
 */
public class BaseDomainTest extends BaseIntegrationTest {

    @Autowired protected DepartmentRepository departmentRepository;
    @Autowired protected EmployeeRepository employeeRepository;
    @Autowired protected ProjectRepository projectRepository;
    @Autowired protected EmployeeProjectRepository employeeProjectRepository;
    @Autowired protected UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Explicit cleanup before each test
        // Order matters due to Foreign Key constraints!
        employeeProjectRepository.deleteAll();
        userRepository.deleteAll();
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
        departmentRepository.deleteAll();
        // Force commit to database
        employeeProjectRepository.flush();
    }

    // --- Level 0 Helpers ---

    protected Department createAndSaveDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return departmentRepository.save(department);
    }

    protected Project createAndSaveProject(String name) {
        Project project = new Project();
        project.setName(name); // No UUID suffix needed anymore
        return projectRepository.save(project);
    }

    // --- Level 1 Helpers ---

    /**
     * Creates an employee with a specific department.
     * Automatically handles the Level 0 dependency (Department).
     */
    protected Employee createAndSaveEmployee(
            String firstName, String lastName, String mail, Department department
    ) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(mail);
        employee.setDepartment(department);

        return employeeRepository.save(employee); // Level 1
    }

    /**
     * Convenience method: Creates a default employee and handles Level 0 dependency automatically.
     */
    protected Employee createAndSaveDefaultEmployee(String email) {
        Department department = createAndSaveDepartment("Engineering"); // Level 0
        return createAndSaveEmployee("John", "Doe", email, department); // Level 1
    }
}