package com.xcale.xcaletest.mapper;

import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.entity.Product;
import com.xcale.xcaletest.util.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper implements Mapper<ProductDTO, Product> {
    @Override
    public ProductDTO toDto(final Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .amount(product.getAmount())
                .description(product.getDescription())
                .build();
    }

    @Override
    public List<ProductDTO> toDTOs(final List<Product> products) {
        return products.stream().map(this::toDto).toList();
    }

    @Override
    public List<Product> toEntities(final List<ProductDTO> dTOs) {
        return dTOs.stream().map(this::toEntity).toList();
    }


    @Override
    public Product toEntity(final ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .amount(productDTO.getAmount())
                .description(productDTO.getDescription())
                .build();
    }
}
