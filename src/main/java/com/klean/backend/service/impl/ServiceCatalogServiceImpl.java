package com.klean.backend.service.impl;

import com.klean.backend.dto.ServiceCatalogDTO;
import com.klean.backend.entity.ServiceCatalogEntity;
import com.klean.backend.entity.SubCategory;
import com.klean.backend.mapper.ServiceCatalogMapper;
import com.klean.backend.repository.ServiceCatalogRepository;
import com.klean.backend.repository.SubCategoryRepository;
import com.klean.backend.service.ServiceCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceCatalogServiceImpl implements ServiceCatalogService {

    private final ServiceCatalogRepository serviceCatalogRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ServiceCatalogServiceImpl(ServiceCatalogRepository serviceCatalogRepository,
                                     SubCategoryRepository subCategoryRepository) {
        this.serviceCatalogRepository = serviceCatalogRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<ServiceCatalogDTO> getServicesBySubCategory(UUID subCategoryId) {
        return serviceCatalogRepository.findBySubCategoryId(subCategoryId)
                .stream()
                .map(ServiceCatalogMapper::toDTO)
                .toList();
    }

    @Override
    public ServiceCatalogDTO getServiceById(UUID serviceId) {
        ServiceCatalogEntity entity = serviceCatalogRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return ServiceCatalogMapper.toDTO(entity);
    }

    @Override
    public ServiceCatalogDTO createService(ServiceCatalogDTO dto, UUID subCategoryId) {

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        ServiceCatalogEntity entity = ServiceCatalogMapper.toEntity(dto);
        entity.setSubCategory(subCategory);

        return ServiceCatalogMapper.toDTO(serviceCatalogRepository.save(entity));
    }

    @Override
    public ServiceCatalogDTO updateService(UUID serviceId, ServiceCatalogDTO dto) {

        ServiceCatalogEntity existing = serviceCatalogRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setImageUrl(dto.getImageUrl());
        existing.setIsActive(dto.getIsActive());

        return ServiceCatalogMapper.toDTO(serviceCatalogRepository.save(existing));
    }

    @Override
    public void deleteService(UUID serviceId) {

        ServiceCatalogEntity entity = serviceCatalogRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        entity.setIsActive(false);
        serviceCatalogRepository.save(entity);
    }
}