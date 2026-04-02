package com.klean.backend.service.impl;

import com.klean.backend.dto.SubCategoryDTO;
import com.klean.backend.entity.Category;
import com.klean.backend.entity.SubCategory;
import com.klean.backend.mapper.SubCategoryMapper;
import com.klean.backend.repository.CategoryRepository;
import com.klean.backend.repository.SubCategoryRepository;
import com.klean.backend.service.SubCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository,
                                  CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SubCategoryDTO> getSubCategoriesByCategory(UUID categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId)
                .stream()
                .map(SubCategoryMapper::toDTO)
                .toList();
    }

    @Override
    public SubCategoryDTO getSubCategoryById(UUID subCategoryId) {
        SubCategory sub = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        return SubCategoryMapper.toDTO(sub);
    }

    @Override
    public SubCategoryDTO createSubCategory(SubCategoryDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        SubCategory entity = SubCategoryMapper.toEntity(dto);
        entity.setCategory(category);

        return SubCategoryMapper.toDTO(subCategoryRepository.save(entity));
    }

    @Override
    public SubCategoryDTO updateSubCategory(UUID subCategoryId, SubCategoryDTO dto) {

        SubCategory existing = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        existing.setName(dto.getName());
        existing.setIsActive(dto.getIsActive());

        return SubCategoryMapper.toDTO(subCategoryRepository.save(existing));
    }

    @Override
    public void deleteSubCategory(UUID subCategoryId) {

        SubCategory sub = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        sub.setIsActive(false);
        subCategoryRepository.save(sub);
    }
}