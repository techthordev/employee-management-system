package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.base.BaseDomainTest;
import br.com.techthordev.backend.entity.Employee;
import br.com.techthordev.backend.entity.EmployeeProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Level 2: EmployeeProfile Repository (1:1)")
class EmployeeProfileRepositoryTest extends BaseDomainTest {

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Test
    @DisplayName("STORY: Create profile for existing employee")
    void shouldCreateProfileForEmployee() {
        Employee employee = createAndSaveDefaultEmployee("profile@techthordev.com");

        EmployeeProfile profile = new EmployeeProfile();
        profile.setEmployee(employee);
        profile.setAddress("Via Roma 1, Cagliari");

        EmployeeProfile saved = employeeProfileRepository.saveAndFlush(profile);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmployee().getId()).isEqualTo(employee.getId());
    }

    @Test
    @DisplayName("CONSTRAINT: Employee can have only one profile")
    void shouldFailWhenCreatingSecondProfileForSameEmployee() {
        Employee employee = createAndSaveDefaultEmployee("duplicate@techthordev.com");

        EmployeeProfile first = new EmployeeProfile();
        first.setEmployee(employee);
        employeeProfileRepository.saveAndFlush(first);

        EmployeeProfile second = new EmployeeProfile();
        second.setEmployee(employee);

        assertThatThrownBy(() ->
                employeeProfileRepository.saveAndFlush(second)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("CONSTRAINT: Profile must fail if employee does not exist")
    void shouldFailIfEmployeeDoesNotExist() {
        Employee ghost = new Employee();
        ghost.setId(999999L);

        EmployeeProfile profile = new EmployeeProfile();
        profile.setEmployee(ghost);

        assertThatThrownBy(() ->
                employeeProfileRepository.saveAndFlush(profile)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }
}
