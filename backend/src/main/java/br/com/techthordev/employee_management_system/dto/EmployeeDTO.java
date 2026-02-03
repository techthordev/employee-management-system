package br.com.techthordev.employee_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {

    private Integer id;

    @NotBlank(message = "First name must not be blank")
    @Size(max = 45, message = "First name must be at most 45 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(max = 45, message = "Last name must be at most 45 characters")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 45, message = "Email must be at most 45 characters")
    private String email;

    public EmployeeDTO() {}

    public EmployeeDTO(Integer id, String firstName, String lastName, String email) {
        this.id = id;

        this.firstName = firstName;

        this.lastName = lastName;

        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
