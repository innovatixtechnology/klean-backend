package com.klean.backend.service;

import com.klean.backend.dto.SubCategoryDTO;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {

    List<SubCategoryDTO> getSubCategoriesByCategory(UUID categoryId);

    SubCategoryDTO getSubCategoryById(UUID subCategoryId);

    SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO);

    SubCategoryDTO updateSubCategory(UUID subCategoryId, SubCategoryDTO subCategoryDTO);

    void deleteSubCategory(UUID subCategoryId);
}