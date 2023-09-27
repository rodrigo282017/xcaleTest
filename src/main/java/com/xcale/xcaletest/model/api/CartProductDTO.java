package com.xcale.xcaletest.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CartProductDTO {
    private CartDTO cartDTO;
    private ProductDTO productDTO;
    private Integer quantity;
}
