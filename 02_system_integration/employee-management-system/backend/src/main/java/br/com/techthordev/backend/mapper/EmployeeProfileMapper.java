package br.com.techthordev.backend.mapper;

import br.com.techthordev.backend.dto.request.EmployeeProfileCreateRequest;
import br.com.techthordev.backend.dto.request.EmployeeProfileUpdateRequest;
import br.com.techthordev.backend.dto.response.EmployeeProfileResponse;
import br.com.techthordev.backend.entity.EmployeeProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeProfileMapper {

    EmployeeProfileResponse toResponse(EmployeeProfile profile);

    @Mapping(target = "employee", ignore = true)
    EmployeeProfile toEntity(EmployeeProfileCreateRequest request);

    @Mapping(target = "employee", ignore = true)
    void updateEntity(EmployeeProfileUpdateRequest request, @MappingTarget EmployeeProfile profile);
}
