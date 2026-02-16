package br.com.techthordev.backend.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private OffsetDateTime createdAt;
}
