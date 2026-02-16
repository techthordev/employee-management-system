package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.base.BaseIntegrationTest;
import br.com.techthordev.backend.entity.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for Department (Level 0).
 */
@DisplayName("Department Repository Tests (Level 0)")
public class DepartmentRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Should save and retrieve a department by its name")
    void shouldSaveAndLoadDepartment() {
        // Given
        Department department = new Department();
        department.setName("Engineering");

        // When
        Department saved = departmentRepository.save(department);
        Optional<Department> found =
                departmentRepository.findById(saved.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("Engineering", found.get().getName());
    }
}
