package br.com.techthordev.backend.dto.response;

import lombok.Data;

@Data
public class EmployeeProfileResponse {

    private Long id;
    private String bio;
    private String skills;
}
