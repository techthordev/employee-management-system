package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.ProjectCreateRequest;
import br.com.techthordev.backend.dto.request.ProjectUpdateRequest;
import br.com.techthordev.backend.dto.response.ProjectResponse;

import java.util.List;


public interface ProjectService {

    ProjectResponse create(ProjectCreateRequest request);

    ProjectResponse findById(Long id);

    List<ProjectResponse> findAll();

    ProjectResponse update(Long id, ProjectUpdateRequest request);

    void delete(Long id);
    
}