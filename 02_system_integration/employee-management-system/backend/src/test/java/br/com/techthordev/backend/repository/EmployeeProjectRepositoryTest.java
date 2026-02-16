package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.base.BaseDomainTest;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.entity.EmployeeProject;
import br.com.techthordev.backend.entity.EmployeeProjectId;
import br.com.techthordev.backend.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.util.Assert.isInstanceOf;

@DisplayName("Level 2: EmployeeProject Repository (N:M Composite PK)")
class EmployeeProjectRepositoryTest extends BaseDomainTest {

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Test
    @DisplayName("STORY: Assign employee to project")
    void shouldAssignEmployeeToProject() {
        Employee employee = createAndSaveDefaultEmployee("nm@techthordev.com");
        Project project = createAndSaveProject("Cloud Migration");

        EmployeeProject assignment = new EmployeeProject();
        assignment.setId(new EmployeeProjectId(employee.getId(), project.getId()));
        assignment.setEmployee(employee);
        assignment.setProject(project);

        employeeProjectRepository.saveAndFlush(assignment);

        List<EmployeeProject> results =
                employeeProjectRepository.findByEmployee_Id(employee.getId());

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProject().getName())
                .isEqualTo("Cloud Migration"); // ← HIER: startsWith → isEqualTo
    }

    @Test
    @DisplayName("CONSTRAINT: Duplicate employee-project assignment is ignored")
    void shouldPreventDuplicateAssignment() {
        Employee employee = createAndSaveDefaultEmployee("dup@techthordev.com");
        Project project = createAndSaveProject("Platform");

        EmployeeProjectId id =
                new EmployeeProjectId(employee.getId(), project.getId());

        EmployeeProject first = new EmployeeProject();
        first.setId(id);
        first.setEmployee(employee);
        first.setProject(project);
        employeeProjectRepository.saveAndFlush(first);

        EmployeeProject duplicate = new EmployeeProject();
        duplicate.setId(id);
        duplicate.setEmployee(employee);
        duplicate.setProject(project);
        employeeProjectRepository.saveAndFlush(duplicate);

        List<EmployeeProject> results =
                employeeProjectRepository.findByEmployee_Id(employee.getId());

        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("CONSTRAINT: Assignment must fail if project does not exist")
    void shouldFailIfProjectDoesNotExist() {
        Employee employee = createAndSaveDefaultEmployee("ghost@techthordev.com");

        Project ghostProject = new Project();
        ghostProject.setId(999999L);

        EmployeeProject assignment = new EmployeeProject();
        assignment.setId(new EmployeeProjectId(employee.getId(), ghostProject.getId()));
        assignment.setEmployee(employee);
        assignment.setProject(ghostProject);

        assertThatThrownBy(() ->
                employeeProjectRepository.saveAndFlush(assignment)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }
}
