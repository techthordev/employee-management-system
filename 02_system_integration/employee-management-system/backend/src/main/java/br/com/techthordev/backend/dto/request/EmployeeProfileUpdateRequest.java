package br.com.techthordev.backend.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeProfileUpdateRequest {

    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    private String bio;

    @Size(max = 500, message = "Skills cannot exceed 500 characters")
    private String skills;
}
