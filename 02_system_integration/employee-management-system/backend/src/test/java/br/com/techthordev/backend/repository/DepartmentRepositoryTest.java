package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldSaveAndLoadDepartment() {
        Department department = new Department();
        department.setName("Engineering");

        Department saved = departmentRepository.save(department);

        Optional<Department> found =
                departmentRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Engineering", found.get().getName());

    }
}
