package br.com.techthordev.employee_management_system.views;

import br.com.techthordev.employee_management_system.dto.EmployeeDTO;
import br.com.techthordev.employee_management_system.entity.Employee;
import br.com.techthordev.employee_management_system.service.EmployeeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Pageable;

/**
 * Admin view to manage employees using Vaadin 25 Flow.
 */
@Route("")
@RolesAllowed("ADMIN")
@PageTitle("Employee Management | Admin")
public class AdminMainView extends VerticalLayout {

    private final EmployeeService employeeService;
    private final Grid<EmployeeDTO> employeeGrid = new Grid<>(EmployeeDTO.class);
    private final TextField filterText = new TextField();

    // Spring Boot 4 automatically injects the service
    public AdminMainView(EmployeeService employeeService) {
        this.employeeService = employeeService;

        setSizeFull();
        configureGrid();
        configureFilter();

        add(
                new H1("IT Support - Employee Management"),
                filterText,
                employeeGrid
        );

        updateList();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name or email...");
        filterText.setWidth("250px");
        filterText.setClearButtonVisible(true);

        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
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

        String searchTerm = filterText.getValue();

        // Using Pageable.unpaged() to get all records for the admin overview
        var employeePage = (searchTerm == null || searchTerm.isEmpty())
                ? employeeService.getAllEmployees(Pageable.unpaged())
                : employeeService.searchEmployees(searchTerm, Pageable.unpaged());

        // Page provides a getContent() method to get the actual List<EmployeeDTO>
        employeeGrid.setItems(employeePage.getContent());
    }
}
