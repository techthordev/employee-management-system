package br.com.techthordev.backend.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private DepartmentResponse department;
    private OffsetDateTime createdAt;
    private Long version;
}
