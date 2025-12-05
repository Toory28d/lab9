package com.example.demo.service.impl;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDTO> getTasksByProject(Long projectId) {
        return taskMapper.toDtos(taskRepository.findByProjectId(projectId));
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElse(null);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO, Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) return null;

        Task task = taskMapper.toEntity(taskDTO);
        task.setProject(project);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskData) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return null;

        task.setTitle(taskData.getTitle());
        task.setDetails(taskData.getDetails());
        task.setStatus(taskData.getStatus());

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public boolean deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return false;

        taskRepository.delete(task);
        return true;
    }
}