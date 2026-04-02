package com.klean.backend.service;

import com.klean.backend.dto.ServiceCatalogDTO;

import java.util.List;
import java.util.UUID;

public interface ServiceCatalogService {

    List<ServiceCatalogDTO> getServicesBySubCategory(UUID subCategoryId);

    ServiceCatalogDTO getServiceById(UUID serviceId);

    ServiceCatalogDTO createService(ServiceCatalogDTO serviceDTO, UUID subCategoryId);

    ServiceCatalogDTO updateService(UUID serviceId, ServiceCatalogDTO serviceDTO);

    void deleteService(UUID serviceId);
}