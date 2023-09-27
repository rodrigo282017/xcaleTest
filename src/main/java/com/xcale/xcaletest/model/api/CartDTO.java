package com.xcale.xcaletest.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
public class CartDTO {
    private UUID id;
    private List<ProductDTO> products;
}
