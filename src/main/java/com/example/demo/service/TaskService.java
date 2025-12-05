package com.example.demo.service;

import com.example.demo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasksByProject(Long projectId);
    TaskDTO getTaskById(Long id);
    TaskDTO createTask(TaskDTO task, Long projectId);
    TaskDTO updateTask(Long id, TaskDTO task);
    boolean deleteTask(Long id);
}