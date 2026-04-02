package com.klean.backend.mapper;

import com.klean.backend.dto.ServiceCatalogDTO;
import com.klean.backend.entity.ServiceCatalogEntity;

public class ServiceCatalogMapper {

    public static ServiceCatalogDTO toDTO(ServiceCatalogEntity entity) {
        ServiceCatalogDTO dto = new ServiceCatalogDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsActive(entity.getIsActive());

        if (entity.getSubCategory() != null) {
            dto.setSubCategoryId(entity.getSubCategory().getId());
        }

        return dto;
    }

    public static ServiceCatalogEntity toEntity(ServiceCatalogDTO dto) {
        ServiceCatalogEntity entity = new ServiceCatalogEntity();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());
        entity.setIsActive(dto.getIsActive());

        return entity;
    }
}