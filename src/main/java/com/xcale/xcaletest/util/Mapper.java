package com.xcale.xcaletest.util;

import java.util.List;

public interface Mapper<DTO, Entity> {
    DTO toDto(Entity entity);

    List<DTO> toDTOs(List<Entity> entities);

    List<Entity> toEntities(List<DTO> dTOs);
    Entity toEntity(DTO dto);
}

