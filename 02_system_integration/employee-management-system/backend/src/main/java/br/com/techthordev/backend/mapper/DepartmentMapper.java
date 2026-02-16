package br.com.techthordev.backend.mapper;

import br.com.techthordev.backend.dto.request.DepartmentCreateRequest;
import br.com.techthordev.backend.dto.request.DepartmentUpdateRequest;
import br.com.techthordev.backend.dto.response.DepartmentResponse;
import br.com.techthordev.backend.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentResponse toResponse(Department department);

    Department toEntity(DepartmentCreateRequest request);

    void updateEntity(DepartmentUpdateRequest request,
                      @MappingTarget Department department);

}
