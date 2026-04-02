package com.klean.backend.controller;

import com.klean.backend.dto.CategoryDTO;
import com.klean.backend.service.CategoryService;
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
@Tag(name = "Category API", description = "Operations for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ===============================
    // PUBLIC APIs
    // ===============================

    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "200", description = "Successfully fetched categories")
    @GetMapping("/public/categories")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/public/categories/{categoryId}")
    public CategoryDTO getCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable UUID categoryId) {
        return categoryService.getCategoryById(categoryId);
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
            summary = "Create category (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Category created")
    @PostMapping("/admin/categories")
    public CategoryDTO createCategory(
            @RequestHeader("Authorization") String token,
            @RequestBody CategoryDTO dto
    ) {
        validateAdmin(token.replace("Bearer ", ""));
        return categoryService.createCategory(dto);
    }

    @Operation(
            summary = "Update category (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/admin/categories/{categoryId}")
    public CategoryDTO updateCategory(
            @RequestHeader("Authorization") String token,
            @PathVariable UUID categoryId,
            @RequestBody CategoryDTO dto
    ) {
        validateAdmin(token.replace("Bearer ", ""));
        return categoryService.updateCategory(categoryId, dto);
    }

    @Operation(
            summary = "Delete category (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/admin/categories/{categoryId}")
    public String deleteCategory(
            @RequestHeader("Authorization") String token,
            @PathVariable UUID categoryId
    ) {
        validateAdmin(token.replace("Bearer ", ""));
        categoryService.deleteCategory(categoryId);
        return "Category deleted";
    }
}