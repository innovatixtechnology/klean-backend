package com.klean.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private Boolean isActive = true;
}