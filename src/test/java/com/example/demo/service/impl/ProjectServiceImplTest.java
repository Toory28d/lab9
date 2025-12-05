package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Project;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private ProjectDTO projectDTO;
    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Test Category", "Desc", new ArrayList<>());
        categoryDTO = new CategoryDTO(1L, "Test Category", "Desc");

        project = new Project(1L, "Test Project", "Description", new ArrayList<>(), new ArrayList<>());
        projectDTO = new ProjectDTO(1L, "Test Project", "Description", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void getAllProjects() {
        List<Project> projects = Collections.singletonList(project);
        List<ProjectDTO> dtos = Collections.singletonList(projectDTO);

        when(projectRepository.findAll()).thenReturn(projects);
        when(projectMapper.toDtos(projects)).thenReturn(dtos);

        List<ProjectDTO> result = projectService.getAllProjects();
        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).getName());
    }

    @Test
    void getProjectById_Found() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.getProjectById(1L);
        assertNotNull(result);
        assertEquals("Test Project", result.getName());
    }

    @Test
    void getProjectById_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectDTO result = projectService.getProjectById(1L);
        assertNull(result);
    }

    @Test
    void createProject_WithCategories() {
        projectDTO.setCategories(Collections.singletonList(categoryDTO));

        when(projectMapper.toEntity(projectDTO)).thenReturn(project);
        when(categoryRepository.findAllById(any())).thenReturn(Collections.singletonList(category));
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.createProject(projectDTO);
        assertNotNull(result);
        assertEquals(1, project.getCategories().size());
    }

    @Test
    void createProject_WithoutCategories() {
        when(projectMapper.toEntity(projectDTO)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.createProject(projectDTO);
        assertNotNull(result);
    }

    @Test
    void updateProject_Found() {
        ProjectDTO updatedDTO = new ProjectDTO(1L, "Updated Name", "Updated Desc", null, null);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(updatedDTO);

        ProjectDTO result = projectService.updateProject(1L, updatedDTO);
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void updateProject_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectDTO result = projectService.updateProject(1L, projectDTO);
        assertNull(result);
    }

    @Test
    void deleteProject_Found() {
        project.setCategories(Collections.singletonList(category));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        boolean result = projectService.deleteProject(1L);
        assertTrue(result);
        verify(projectRepository).delete(project);
        assertTrue(project.getCategories().isEmpty());
    }

    @Test
    void deleteProject_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = projectService.deleteProject(1L);
        assertFalse(result);
    }

    @Test
    void addCategoryToProject_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.addCategoryToProject(1L, 1L);
        assertNotNull(result);
        assertTrue(project.getCategories().contains(category));
        assertTrue(category.getProjects().contains(project));
    }

    @Test
    void addCategoryToProject_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectDTO result = projectService.addCategoryToProject(1L, 1L);
        assertNull(result);
    }

    @Test
    void removeCategoryFromProject_Success() {
        project.getCategories().add(category);
        category.getProjects().add(project);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.removeCategoryFromProject(1L, 1L);
        assertNotNull(result);
        assertFalse(project.getCategories().contains(category));
        assertFalse(category.getProjects().contains(project));
    }

    @Test
    void removeCategoryFromProject_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectDTO result = projectService.removeCategoryFromProject(1L, 1L);
        assertNull(result);
    }
}