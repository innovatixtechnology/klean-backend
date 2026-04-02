package com.klean.backend.service;

import com.klean.backend.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(UUID categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(UUID categoryId, CategoryDTO categoryDTO);

    void deleteCategory(UUID categoryId);
}