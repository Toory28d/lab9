package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Project;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectMapper.toDtos(projectRepository.findAll());
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDto)
                .orElse(null);
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);

        if (projectDTO.getCategories() != null && !projectDTO.getCategories().isEmpty()) {
            List<Long> ids = projectDTO.getCategories().stream()
                    .map(CategoryDTO::getId)
                    .collect(Collectors.toList());

            List<Category> realCategories = categoryRepository.findAllById(ids);
            project.setCategories(realCategories);
        }

        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectData) {
        Project existing = projectRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(projectData.getName());
        existing.setDescription(projectData.getDescription());

        if (projectData.getCategories() != null) {
            List<Long> ids = projectData.getCategories().stream()
                    .map(CategoryDTO::getId)
                    .collect(Collectors.toList());

            List<Category> updatedCategories = categoryRepository.findAllById(ids);
            existing.setCategories(updatedCategories);
        }

        return projectMapper.toDto(projectRepository.save(existing));
    }

    @Override
    public boolean deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) return false;

        project.getCategories().clear();
        projectRepository.delete(project);
        return true;
    }

    @Override
    public ProjectDTO addCategoryToProject(Long projectId, Long categoryId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (project == null || category == null) return null;

        project.getCategories().add(category);
        category.getProjects().add(project);

        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDTO removeCategoryFromProject(Long projectId, Long categoryId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (project == null || category == null) return null;

        project.getCategories().remove(category);
        category.getProjects().remove(project);

        return projectMapper.toDto(projectRepository.save(project));
    }
}