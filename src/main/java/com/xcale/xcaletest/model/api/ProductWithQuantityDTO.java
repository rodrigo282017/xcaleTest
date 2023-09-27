package com.xcale.xcaletest.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Builder
@ToString
public class ProductWithQuantityDTO {
    private UUID productId;
    private int quantity;
}
