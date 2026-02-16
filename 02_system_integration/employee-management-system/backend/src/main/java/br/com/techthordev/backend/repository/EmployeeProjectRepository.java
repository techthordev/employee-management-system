package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.EmployeeProject;
import br.com.techthordev.backend.entity.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeProjectRepository
        extends JpaRepository<EmployeeProject, EmployeeProjectId> {

    List<EmployeeProject> findByEmployee_Id(Long employeeId);

    List<EmployeeProject> findByProject_Id(Long projectId);

    boolean existsByEmployee_IdAndProject_Id(Long employeeId, Long projectId);
}

