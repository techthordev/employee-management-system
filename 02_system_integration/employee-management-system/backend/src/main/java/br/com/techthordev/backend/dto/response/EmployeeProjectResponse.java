package br.com.techthordev.backend.dto.response;

import lombok.Data;

@Data
public class EmployeeProjectResponse {

    private Long employeeId;
    private Long projectId;
    private String projectName;
}
