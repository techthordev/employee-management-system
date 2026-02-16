package br.com.techthordev.backend.service;

import br.com.techthordev.backend.base.BaseDomainTest;
import br.com.techthordev.backend.dto.request.DepartmentCreateRequest;
import br.com.techthordev.backend.dto.request.DepartmentUpdateRequest;
import br.com.techthordev.backend.dto.response.DepartmentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DepartmentService - Business Logic Tests")
class DepartmentServiceTest extends BaseDomainTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    @DisplayName("Should create department successfully")
    void shouldCreateDepartment() {
        DepartmentCreateRequest request = new DepartmentCreateRequest();
        request.setName("Engineering");

        DepartmentResponse response = departmentService.create(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Engineering");
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getVersion()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should find department by ID")
    void shouldFindDepartmentById() {
        DepartmentCreateRequest request = new DepartmentCreateRequest();
        request.setName("HR");
        DepartmentResponse created = departmentService.create(request);

        DepartmentResponse found = departmentService.findById(created.getId());

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(created.getId());
        assertThat(found.getName()).isEqualTo("HR");
    }

    @Test
    @DisplayName("Should throw exception when department not found")
    void shouldThrowWhenDepartmentNotFound() {
        assertThatThrownBy(() -> departmentService.findById(99999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Should find all departments")
    void shouldFindAllDepartments() {
        DepartmentCreateRequest request1 = new DepartmentCreateRequest();
        request1.setName("Engineering");
        departmentService.create(request1);

        DepartmentCreateRequest request2 = new DepartmentCreateRequest();
        request2.setName("Marketing");
        departmentService.create(request2);

        List<DepartmentResponse> departments = departmentService.findAll();

        assertThat(departments).hasSize(2);
        assertThat(departments)
                .extracting(DepartmentResponse::getName)
                .containsExactlyInAnyOrder("Engineering", "Marketing");
    }

    @Test
    @DisplayName("Should update department successfully")
    void shouldUpdateDepartment() {
        DepartmentCreateRequest createRequest = new DepartmentCreateRequest();
        createRequest.setName("Sales");
        DepartmentResponse created = departmentService.create(createRequest);

        DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest();
        updateRequest.setName("Sales & Marketing");

        DepartmentResponse updated = departmentService.update(created.getId(), updateRequest);

        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo("Sales & Marketing");
//        assertThat(updated.getVersion()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent department")
    void shouldThrowWhenUpdatingNonExistent() {
        DepartmentUpdateRequest request = new DepartmentUpdateRequest();
        request.setName("New Name");

        assertThatThrownBy(() -> departmentService.update(99999L, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Should delete department successfully")
    void shouldDeleteDepartment() {
        DepartmentCreateRequest request = new DepartmentCreateRequest();
        request.setName("Temporary");
        DepartmentResponse created = departmentService.create(request);

        departmentService.delete(created.getId());

        assertThatThrownBy(() -> departmentService.findById(created.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent department")
    void shouldThrowWhenDeletingNonExistent() {
        assertThatThrownBy(() -> departmentService.delete(99999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }
}