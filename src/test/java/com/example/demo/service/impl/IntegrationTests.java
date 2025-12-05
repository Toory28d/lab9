package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.TaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void categoryControllerIntegration() throws Exception {
        // Create
        CategoryDTO createDTO = new CategoryDTO(null, "Integration Category", "Desc");
        String createJson = objectMapper.writeValueAsString(createDTO);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Category"));

        // Get All
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assume ID 3 from initial data + created one
        // Get By ID (adjust ID based on DB state, for demo assume 1)
        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

        // Update
        CategoryDTO updateDTO = new CategoryDTO(null, "Updated Integration", "Updated Desc");
        String updateJson = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Integration"));

        // Delete
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk());
    }

    @Test
    void projectControllerIntegration() throws Exception {
        // Create
        ProjectDTO createDTO = new ProjectDTO(null, "Integration Project", "Desc", null, null);
        String createJson = objectMapper.writeValueAsString(createDTO);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Project"));

        // Get All
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Get By ID
        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

        // Update
        ProjectDTO updateDTO = new ProjectDTO(null, "Updated Project", "Updated Desc", null, null);
        String updateJson = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Project"));

        // Add Category (assume category ID 1 exists)
        mockMvc.perform(post("/api/projects/1/categories/1"))
                .andExpect(status().isOk());

        // Remove Category
        mockMvc.perform(delete("/api/projects/1/categories/1"))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk());
    }

    @Test
    void taskControllerIntegration() throws Exception {
        // Assume project ID 1 exists

        // Create
        TaskDTO createDTO = new TaskDTO(null, "Integration Task", "Details", 0, null);
        String createJson = objectMapper.writeValueAsString(createDTO);

        mockMvc.perform(post("/api/tasks/project/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Task"));

        // Get By Project
        mockMvc.perform(get("/api/tasks/project/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Get By ID (assume created ID is 1 for demo)
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

        // Update
        TaskDTO updateDTO = new TaskDTO(null, "Updated Task", "Updated Details", 1, null);
        String updateJson = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));

        // Delete
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }

    // Add more for WebProjectController if needed, testing Thymeleaf endpoints with HTML content
}