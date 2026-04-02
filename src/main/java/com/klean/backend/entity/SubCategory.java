package com.klean.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;
@Entity
@Data
public class SubCategory {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Category category;

    private String name;
    private Boolean isActive = true;
}