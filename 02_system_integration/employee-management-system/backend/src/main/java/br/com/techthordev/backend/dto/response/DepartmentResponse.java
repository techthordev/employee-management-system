package br.com.techthordev.backend.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DepartmentResponse {

    private Long id;
    private String name;
    private OffsetDateTime createdAt;
    private Long version;

}
