package com.xcale.xcaletest.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartDTO {
    private List<ProductAndQuantity> productQuantity;

    @Data
    @Builder
    public static class ProductAndQuantity {
        private UUID productId;
        private int quantity;
    }
}
