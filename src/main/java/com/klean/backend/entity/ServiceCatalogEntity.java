package com.klean.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Entity
@Data
public class ServiceCatalogEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private SubCategory subCategory;

    private String name;

    @Column(length = 2000)
    private String description;
    private Double price;
    private String imageUrl;
    private Boolean isActive = true;
}
