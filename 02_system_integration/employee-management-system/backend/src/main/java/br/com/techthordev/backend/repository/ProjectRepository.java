package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Custom query example: Find project by name
    java.util.Optional<Project> findByName(String name);
}
