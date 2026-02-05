package br.com.techthordev.employee_management_system.controller;

import br.com.techthordev.employee_management_system.config.ApiVersion;
import br.com.techthordev.employee_management_system.dto.EmployeeDTO;
import br.com.techthordev.employee_management_system.exception.ApiError;
import br.com.techthordev.employee_management_system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@ApiVersion(1)
@Tag(
        name = "Employee API v1",
        description = "Operations related to employee management"
)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // -------------------- GET ALL --------------------

    @Operation(
            summary = "Get all employees",
            description = """
                    Returns a paginated list of employees.
                    Supports pagination and dynamic sorting via query parameters.
                    """
    )
    @Parameters({
            @Parameter(name = "page", description = "Zero-based page index", example = "0"),
            @Parameter(name = "size", description = "Number of records per page", example = "20"),
            @Parameter(
                    name = "sort",
                    description = "Sorting criteria: property,(asc|desc)",
                    example = "lastName,asc"
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employees retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    // -------------------- GET BY ID --------------------

    @Operation(summary = "Get employee by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // -------------------- CREATE --------------------

    @Operation(summary = "Create a new employee")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid employee data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------------------- UPDATE --------------------

    @Operation(summary = "Update an existing employee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid employee data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    // -------------------- DELETE --------------------

    @Operation(summary = "Delete employee by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
