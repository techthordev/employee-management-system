package br.com.techthordev.employee_management_system.views;

import br.com.techthordev.employee_management_system.dto.EmployeeDTO;
import br.com.techthordev.employee_management_system.entity.Employee;
import br.com.techthordev.employee_management_system.service.EmployeeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.Pageable;

/**
 * Admin view to manage employees using Vaadin 25 Flow.
 */
@Route("")
@PageTitle("Employee Management | Admin")
public class AdminMainView extends VerticalLayout {

    private final EmployeeService employeeService;
    private final Grid<EmployeeDTO> employeeGrid = new Grid<>(EmployeeDTO.class);

    // Spring Boot 4 automatically injects the service
    public AdminMainView(EmployeeService employeeService) {
        this.employeeService = employeeService;

        setSizeFull();
        configureGrid();
        updateList();

        add(
                new H1("IT Support - Employee Management"),
                employeeGrid
        );
    }

    private void configureGrid() {
        employeeGrid.addClassNames("grid-employee");
        employeeGrid.setSizeFull();

        // Explicitly defining columns to show only relevant support data
        employeeGrid.setColumns("id", "firstName", "lastName", "email");

        // Automatic column sizing for better readability on your Fedora screen
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        // Using Pageable.unpaged() to get all records for the admin overview
        var employeePage = employeeService.getAllEmployees(Pageable.unpaged());

        // Page provides a getContent() method to get the actual List<EmployeeDTO>
        employeeGrid.setItems(employeePage.getContent());
    }
}
