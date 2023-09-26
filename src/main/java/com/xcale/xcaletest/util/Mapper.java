package com.xcale.xcaletest.util;

import java.util.List;

/**
 * Generic Mapper interface to convert between DTOs (Data Transfer Objects) and Entities.
 * This interface defines methods to convert DTOs to Entities and vice versa, as well as lists of DTOs and Entities.
 *
 * @param <DTO>    The type of DTO (Data Transfer Object).
 * @param <Entity> The type of Entity.
 */
public interface Mapper<DTO, Entity> {
    /**
     * Converts an Entity to its corresponding DTO.
     *
     * @param entity The Entity to be converted.
     * @return The DTO representing the Entity.
     */
    DTO toDto(Entity entity);

    /**
     * Converts a list of Entities to a list of DTOs.
     *
     * @param entities The list of Entities to be converted.
     * @return The list of DTOs representing the Entities.
     */
    List<DTO> toDTOs(List<Entity> entities);

    /**
     * Converts a list of DTOs to a list of Entities.
     *
     * @param dTOs The list of DTOs to be converted.
     * @return The list of Entities representing the DTOs.
     */
    List<Entity> toEntities(List<DTO> dTOs);
    /**
     * Converts a DTO to its corresponding Entity.
     *
     * @param dto The DTO to be converted.
     * @return The Entity representing the DTO.
     */
    Entity toEntity(DTO dto);
}

