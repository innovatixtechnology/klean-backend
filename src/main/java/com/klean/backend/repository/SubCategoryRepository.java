package com.klean.backend.repository;

import com.klean.backend.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
    // Fetch subcategories under a category
    List<SubCategory> findByCategoryId(UUID categoryId);

    // Only active ones (recommended)
    List<SubCategory> findByCategoryIdAndIsActiveTrue(UUID categoryId);
}
