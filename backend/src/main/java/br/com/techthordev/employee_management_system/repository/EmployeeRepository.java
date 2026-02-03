package br.com.techthordev.employee_management_system.repository;

import br.com.techthordev.employee_management_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository for Employee entities.
 *
 * Responsible for data access only.
 * No business logic should be placed here.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Everything CRUD-related is inherited from JpaRepository

}
