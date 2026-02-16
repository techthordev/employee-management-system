package br.com.techthordev.backend.service;

import br.com.techthordev.backend.dto.request.ProjectCreateRequest;
import br.com.techthordev.backend.dto.request.ProjectUpdateRequest;
import br.com.techthordev.backend.dto.response.ProjectResponse;
import br.com.techthordev.backend.entity.Project;
import br.com.techthordev.backend.mapper.ProjectMapper;
import br.com.techthordev.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponse create(ProjectCreateRequest request) {
        Project project = projectMapper.toEntity(request);
        Project saved = projectRepository.save(project);
        return projectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProjectResponse findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found: " + id));
        return projectMapper.toResponse(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public ProjectResponse update(Long id, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found: " + id));

        projectMapper.updateEntity(request, project);
        Project updated = projectRepository.save(project);
        return projectMapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }
}