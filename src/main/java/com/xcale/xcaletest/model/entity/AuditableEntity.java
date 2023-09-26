package com.xcale.xcaletest.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.Instant;

/**
 * MappedSuperclass for auditable entities, tracking creation and update timestamps.
 * This class provides fields to track the creation and last update timestamps for entities.
 * It uses JPA annotations for persistence and extends the auditing behavior to its subclasses.
 */
@Data
@MappedSuperclass
public class AuditableEntity {

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    /**
     * Handles updating the 'updatedAt' timestamp before an entity is updated.
     * This method is annotated with {@link PreUpdate} to automatically update the 'updatedAt' field
     * before the entity is updated in the database.
     */
    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }
}
