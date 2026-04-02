package com.klean.backend.mapper;

import com.klean.backend.dto.SubCategoryDTO;
import com.klean.backend.entity.SubCategory;

public class SubCategoryMapper {

    public static SubCategoryDTO toDTO(SubCategory entity) {
        SubCategoryDTO dto = new SubCategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsActive(entity.getIsActive());

        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
        }

        return dto;
    }

    public static SubCategory toEntity(SubCategoryDTO dto) {
        SubCategory entity = new SubCategory();
        entity.setName(dto.getName());
        entity.setIsActive(dto.getIsActive());
        return entity;
    }
}