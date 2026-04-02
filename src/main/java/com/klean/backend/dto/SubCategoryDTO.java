package com.klean.backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SubCategoryDTO {

    private UUID id;

    private UUID categoryId;

    private String name;

    private Boolean isActive;
}