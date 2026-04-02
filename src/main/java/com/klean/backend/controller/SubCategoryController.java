package com.klean.backend.controller;

import com.klean.backend.dto.SubCategoryDTO;
import com.klean.backend.service.SubCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "SubCategory API", description = "Operations for managing subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    // ===============================
    // PUBLIC APIs
    // ===============================

    @Operation(summary = "Get all subcategories for a category")
    @ApiResponse(responseCode = "200", description = "Successfully fetched subcategories")
    @GetMapping("/public/categories/{categoryId}/subcategories")
    public List<SubCategoryDTO> getSubCategories(
            @Parameter(description = "Category ID", required = true)
            @PathVariable UUID categoryId) {
        return subCategoryService.getSubCategoriesByCategory(categoryId);
    }

    @Operation(summary = "Get subcategory by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubCategory found"),
            @ApiResponse(responseCode = "404", description = "SubCategory not found")
    })
    @GetMapping("/public/subcategories/{subCategoryId}")
    public SubCategoryDTO getSubCategory(
            @Parameter(description = "SubCategory ID", required = true)
            @PathVariable UUID subCategoryId) {
        return subCategoryService.getSubCategoryById(subCategoryId);
    }

    // ===============================
    // ADMIN APIs
    // ===============================

    private static final String ADMIN_TOKEN = "ADMIN_SECRET_123";

    private void validateAdmin(String token) {
        if (token == null || !token.equals(ADMIN_TOKEN)) {
            throw new RuntimeException("Unauthorized");
        }
    }

    @Operation(
            summary = "Create subcategory (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "SubCategory created")
    @PostMapping("/admin/subcategories")
    public SubCategoryDTO createSubCategory(
            @RequestHeader("Authorization") String token,
            @RequestBody SubCategoryDTO dto
    ) {
        validateAdmin(extractToken(token));
        return subCategoryService.createSubCategory(dto);
    }

    @Operation(
            summary = "Update subcategory (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/admin/subcategories/{subCategoryId}")
    public SubCategoryDTO updateSubCategory(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "SubCategory ID", required = true)
            @PathVariable UUID subCategoryId,
            @RequestBody SubCategoryDTO dto
    ) {
        validateAdmin(extractToken(token));
        return subCategoryService.updateSubCategory(subCategoryId, dto);
    }

    @Operation(
            summary = "Delete subcategory (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/admin/subcategories/{subCategoryId}")
    public String deleteSubCategory(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "SubCategory ID", required = true)
            @PathVariable UUID subCategoryId
    ) {
        validateAdmin(extractToken(token));
        subCategoryService.deleteSubCategory(subCategoryId);
        return "SubCategory deleted";
    }

    // ===============================
    // HELPER METHOD (important fix)
    // ===============================

    private String extractToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        return token.substring(7);
    }
}