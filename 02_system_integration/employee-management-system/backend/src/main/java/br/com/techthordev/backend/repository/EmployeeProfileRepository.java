package br.com.techthordev.backend.repository;

import br.com.techthordev.backend.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeProfileRepository
        extends JpaRepository<EmployeeProfile, Long> {

    Optional<EmployeeProfile> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeId(Long employeeId);
}
