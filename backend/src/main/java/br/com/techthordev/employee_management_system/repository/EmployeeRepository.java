package br.com.techthordev.employee_management_system.repository;

import br.com.techthordev.employee_management_system.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Repository for Employee entities.
 *
 * Responsible for data access only.
 * No business logic should be placed here.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Everything CRUD-related is inherited from JpaRepository

    /**
     * Search employees by first name, last name, or email.
     * Case-insensitive search using PostgreSQL lower() function.
     */
    @Query("SELECT e FROM Employee e " +
            "WHERE lower(e.firstName) LIKE lower(concat('%', :searchTerm, '%')) " +
            "OR lower(e.lastName) LIKE lower(concat('%', :searchTerm, '%')) " +
            "OR lower(e.email) LIKE lower(concat('%', :searchTerm, '%'))")
    Page<Employee> search(@Param("searchTerm") String searchTerm, Pageable pageable);

}
