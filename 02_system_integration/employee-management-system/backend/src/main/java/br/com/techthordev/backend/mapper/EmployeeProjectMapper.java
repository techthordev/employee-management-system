package br.com.techthordev.backend.mapper;

import br.com.techthordev.backend.dto.response.EmployeeProjectResponse;
import br.com.techthordev.backend.entity.EmployeeProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeProjectMapper {

    @Mapping(target = "employeeId", source = "id.employeeId")
    @Mapping(target = "projectId", source = "id.projectId")
    @Mapping(target = "projectName", source = "project.name")
    EmployeeProjectResponse toResponse(EmployeeProject employeeProject);
}
