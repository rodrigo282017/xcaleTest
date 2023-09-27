package com.xcale.xcaletest.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@ToString
public class ProductDTO {
    private UUID id;
    private String description;
    private BigDecimal amount;
}
