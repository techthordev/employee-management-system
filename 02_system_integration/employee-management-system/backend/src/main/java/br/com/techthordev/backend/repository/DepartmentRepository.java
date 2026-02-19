package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Standard CRUD (Create, Read, Update, Delete) is automatically available
}
