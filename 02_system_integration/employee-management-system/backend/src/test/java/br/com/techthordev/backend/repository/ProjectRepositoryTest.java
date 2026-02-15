package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.Project;
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
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void shouldSaveAndLoadProject() {

        Project project = new Project();
        project.setName("HR System");

        Project saved = projectRepository.save(project);

        Optional<Project> found =
                projectRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("HR System",found.get().getName());

    }

}
