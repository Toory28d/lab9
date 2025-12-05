package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO updateCategory(Long id, CategoryDTO category);

    boolean deleteCategory(Long id);
}
