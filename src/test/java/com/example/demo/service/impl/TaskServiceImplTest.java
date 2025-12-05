package com.example.demo.service.impl;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDTO taskDTO;
    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project(1L, "Test Project", "Desc", null, null);

        task = new Task(1L, "Test Task", "Details", 0, project);
        taskDTO = new TaskDTO(1L, "Test Task", "Details", 0, 1L);
    }

    @Test
    void getTasksByProject() {
        List<Task> tasks = Collections.singletonList(task);
        List<TaskDTO> dtos = Collections.singletonList(taskDTO);

        when(taskRepository.findByProjectId(1L)).thenReturn(tasks);
        when(taskMapper.toDtos(tasks)).thenReturn(dtos);

        List<TaskDTO> result = taskService.getTasksByProject(1L);
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void getTaskById_Found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.getTaskById(1L);
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void getTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO result = taskService.getTaskById(1L);
        assertNull(result);
    }

    @Test
    void createTask_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskMapper.toEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.createTask(taskDTO, 1L);
        assertNotNull(result);
        assertEquals(project, task.getProject());
    }

    @Test
    void createTask_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO result = taskService.createTask(taskDTO, 1L);
        assertNull(result);
    }

    @Test
    void updateTask_Found() {
        TaskDTO updatedDTO = new TaskDTO(1L, "Updated Title", "Updated Details", 1, 1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(updatedDTO);

        TaskDTO result = taskService.updateTask(1L, updatedDTO);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void updateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO result = taskService.updateTask(1L, taskDTO);
        assertNull(result);
    }

    @Test
    void deleteTask_Found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        boolean result = taskService.deleteTask(1L);
        assertTrue(result);
        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = taskService.deleteTask(1L);
        assertFalse(result);
    }
}