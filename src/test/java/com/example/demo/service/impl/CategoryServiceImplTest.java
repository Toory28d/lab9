package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Project;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Test Category", "Description", new ArrayList<>());
        categoryDTO = new CategoryDTO(1L, "Test Category", "Description");
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Collections.singletonList(category);
        List<CategoryDTO> dtos = Collections.singletonList(categoryDTO);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDtos(categories)).thenReturn(dtos);

        List<CategoryDTO> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    void getCategoryById_Found() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getCategoryById(1L);
        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        CategoryDTO result = categoryService.getCategoryById(1L);
        assertNull(result);
    }

    @Test
    void createCategory() {
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.createCategory(categoryDTO);
        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    void updateCategory_Found() {
        CategoryDTO updatedDTO = new CategoryDTO(1L, "Updated Name", "Updated Desc");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(updatedDTO);

        CategoryDTO result = categoryService.updateCategory(1L, updatedDTO);
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void updateCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        CategoryDTO result = categoryService.updateCategory(1L, categoryDTO);
        assertNull(result);
    }

    @Test
    void deleteCategory_Found() {
        Project project = new Project();
        project.setCategories(new ArrayList<>(Collections.singletonList(category)));
        category.setProjects(new ArrayList<>(Collections.singletonList(project)));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        boolean result = categoryService.deleteCategory(1L);
        assertTrue(result);
        verify(categoryRepository).delete(category);
        assertTrue(project.getCategories().isEmpty());
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = categoryService.deleteCategory(1L);
        assertFalse(result);
    }
}