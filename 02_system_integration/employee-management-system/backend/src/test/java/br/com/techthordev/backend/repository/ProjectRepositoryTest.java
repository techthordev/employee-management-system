package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.base.BaseIntegrationTest;
import br.com.techthordev.backend.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for Project (Level 0).
 */
@DisplayName("Project Repository Tests (Level 0)")
public class ProjectRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("Should correctly persist a new project")
    void shouldSaveAndLoadProject() {

        // Given
        Project project = new Project();
        project.setName("HR System");

        // When
        Project saved = projectRepository.save(project);
        Optional<Project> found =
                projectRepository.findById(saved.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("HR System",found.get().getName());
    }

}
