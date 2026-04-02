package com.klean.backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ServiceCatalogDTO {

    private UUID id;

    private UUID subCategoryId;

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    private Boolean isActive;
}