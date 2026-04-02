package com.klean.backend.service.impl;

import com.klean.backend.dto.CategoryDTO;
import com.klean.backend.entity.Category;
import com.klean.backend.mapper.CategoryMapper;
import com.klean.backend.repository.CategoryRepository;
import com.klean.backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    @Override
    public CategoryDTO getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category entity = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDTO(categoryRepository.save(entity));
    }

    @Override
    public CategoryDTO updateCategory(UUID categoryId, CategoryDTO dto) {

        Category existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(dto.getName());
        existing.setIsActive(dto.getIsActive());

        return CategoryMapper.toDTO(categoryRepository.save(existing));
    }

    @Override
    public void deleteCategory(UUID categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setIsActive(false);
        categoryRepository.save(category);
    }
}
