package com.example.demo.controller;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping("/")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "index";
    }

    @GetMapping("/projects/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        ProjectDTO project = projectService.getProjectById(id);
        if (project == null) {
            return "redirect:/";
        }
        model.addAttribute("project", project);
        model.addAttribute("tasks", taskService.getTasksByProject(id));
        return "project";
    }

    @PostMapping("/projects/add")
    public String addProject(@RequestParam String name, @RequestParam(required = false) String description) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(name);
        projectDTO.setDescription(description);
        projectService.createProject(projectDTO);
        return "redirect:/";
    }

    @PostMapping("/projects/update")
    public String updateProject(@RequestParam Long id, @RequestParam String name, @RequestParam(required = false) String description) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(name);
        projectDTO.setDescription(description);
        projectService.updateProject(id, projectDTO);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/";
    }

    @PostMapping("/projects/{projectId}/tasks/add")
    public String addTask(@PathVariable Long projectId, @RequestParam String title, @RequestParam(required = false) String details) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(title);
        taskDTO.setDetails(details);
        taskDTO.setStatus(0);
        taskService.createTask(taskDTO, projectId);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam String title, @RequestParam(required = false) String details,
                             @RequestParam int status, @RequestParam Long projectId) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(title);
        taskDTO.setDetails(details);
        taskDTO.setStatus(status);
        taskService.updateTask(id, taskDTO);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id, @RequestParam Long projectId) {
        taskService.deleteTask(id);
        return "redirect:/projects/" + projectId;
    }
}