package com.example.demo.service;

import com.example.demo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllProjects();

    ProjectDTO getProjectById(Long id);

    ProjectDTO createProject(ProjectDTO project);

    ProjectDTO updateProject(Long id, ProjectDTO projectData);

    boolean deleteProject(Long id);

    ProjectDTO addCategoryToProject(Long projectId, Long categoryId);

    ProjectDTO removeCategoryFromProject(Long projectId, Long categoryId);
}