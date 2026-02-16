package br.com.techthordev.backend.mapper;

import br.com.techthordev.backend.dto.request.ProjectCreateRequest;
import br.com.techthordev.backend.dto.request.ProjectUpdateRequest;
import br.com.techthordev.backend.dto.response.ProjectResponse;
import br.com.techthordev.backend.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toResponse(Project project);

    Project toEntity(ProjectCreateRequest request);

    void updateEntity(ProjectUpdateRequest request, @MappingTarget Project project);
}
