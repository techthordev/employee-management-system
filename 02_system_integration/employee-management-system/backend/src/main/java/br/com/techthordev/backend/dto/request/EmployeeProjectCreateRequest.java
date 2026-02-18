package br.com.techthordev.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeProjectCreateRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;
}
