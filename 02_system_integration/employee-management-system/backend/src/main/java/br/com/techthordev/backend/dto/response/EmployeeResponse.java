package br.com.techthordev.backend.dto.response;

import java.time.OffsetDateTime;

public record EmployeeResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    DepartmentResponse department,
    OffsetDateTime createdAt,
    Long version
) {}
