package com.klean.backend.mapper;

import com.klean.backend.dto.CategoryDTO;
import com.klean.backend.entity.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity.setIsActive(dto.getIsActive());
        return entity;
    }
}
