package com.example.demo.controller;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProject(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }

    @PostMapping("/{projectId}/categories/{categoryId}")
    public ProjectDTO addCategoryToProject(@PathVariable Long projectId, @PathVariable Long categoryId) {
        return projectService.addCategoryToProject(projectId, categoryId);
    }

    @DeleteMapping("/{projectId}/categories/{categoryId}")
    public ProjectDTO removeCategoryFromProject(@PathVariable Long projectId, @PathVariable Long categoryId) {
        return projectService.removeCategoryFromProject(projectId, categoryId);
    }
}