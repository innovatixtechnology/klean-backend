package com.klean.backend.controller;

import com.klean.backend.dto.ServiceCatalogDTO;
import com.klean.backend.service.ServiceCatalogService;

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
@Tag(name = "Service API", description = "Operations for managing services")
public class ServiceCatalogController {

    private final ServiceCatalogService serviceCatalogService;

    public ServiceCatalogController(ServiceCatalogService serviceCatalogService) {
        this.serviceCatalogService = serviceCatalogService;
    }

    // ===============================
    // PUBLIC APIs
    // ===============================

    @Operation(summary = "Get all services for a subcategory")
    @ApiResponse(responseCode = "200", description = "Successfully fetched services")
    @GetMapping("/public/subcategories/{subCategoryId}/services")
    public List<ServiceCatalogDTO> getServices(
            @Parameter(description = "SubCategory ID", required = true)
            @PathVariable UUID subCategoryId) {
        return serviceCatalogService.getServicesBySubCategory(subCategoryId);
    }

    @Operation(summary = "Get service by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service found"),
            @ApiResponse(responseCode = "404", description = "Service not found")
    })
    @GetMapping("/public/services/{serviceId}")
    public ServiceCatalogDTO getService(
            @Parameter(description = "Service ID", required = true)
            @PathVariable UUID serviceId) {
        return serviceCatalogService.getServiceById(serviceId);
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
            summary = "Create service under a subcategory (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Service created")
    @PostMapping("/admin/subcategories/{subCategoryId}/services")
    public ServiceCatalogDTO createService(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "SubCategory ID", required = true)
            @PathVariable UUID subCategoryId,
            @RequestBody ServiceCatalogDTO dto
    ) {
        validateAdmin(extractToken(token));
        return serviceCatalogService.createService(dto, subCategoryId);
    }

    @Operation(
            summary = "Update service (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/admin/services/{serviceId}")
    public ServiceCatalogDTO updateService(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Service ID", required = true)
            @PathVariable UUID serviceId,
            @RequestBody ServiceCatalogDTO dto
    ) {
        validateAdmin(extractToken(token));
        return serviceCatalogService.updateService(serviceId, dto);
    }

    @Operation(
            summary = "Delete service (Admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/admin/services/{serviceId}")
    public String deleteService(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Service ID", required = true)
            @PathVariable UUID serviceId
    ) {
        validateAdmin(extractToken(token));
        serviceCatalogService.deleteService(serviceId);
        return "Service deleted";
    }

    // ===============================
    // HELPER METHOD (fixes bug)
    // ===============================

    private String extractToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        return token.substring(7);
    }
}