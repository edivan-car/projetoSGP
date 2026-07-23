package br.com.sgp.model;

import java.time.LocalDateTime;

public class Resource {

    private final int id;
    private final String code;
    private final String legacyCode;
    private final String description;
    private final String category;
    private final String resourceType;
    private final String unitCode;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Resource(
            int id,
            String code,
            String legacyCode,
            String description,
            String category,
            String resourceType,
            String unitCode,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.code = code;
        this.legacyCode = legacyCode;
        this.description = description;
        this.category = category;
        this.resourceType = resourceType;
        this.unitCode = unitCode;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getLegacyCode() {
        return legacyCode;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}