package com.klean.backend.repository;

import com.klean.backend.entity.ServiceCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalogEntity, UUID> {
    // Fetch services under a subcategory
    List<ServiceCatalogEntity> findBySubCategoryId(UUID subCategoryId);

    // Recommended (only active services)
    List<ServiceCatalogEntity> findBySubCategoryIdAndIsActiveTrue(UUID subCategoryId);
}
