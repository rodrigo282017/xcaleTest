package com.xcale.xcaletest.mapper;

import com.xcale.xcaletest.model.api.CartProductDTO;
import com.xcale.xcaletest.model.entity.CartProduct;
import com.xcale.xcaletest.model.entity.CartProductId;
import com.xcale.xcaletest.util.Mapper;

import java.util.List;

public class CartProductMapper implements Mapper<CartProductDTO, CartProduct> {
    @Override
    public CartProductDTO toDto(final CartProduct cartProduct) {
        return CartProductDTO.builder()
                .cartDTO(new CartMapper().toDto(cartProduct.getCart()))
                .quantity(cartProduct.getQuantity())
                .productDTO(new ProductMapper().toDto(cartProduct.getProduct()))
                .build();
    }

    @Override
    public List<CartProductDTO> toDTOs(final List<CartProduct> cartProducts) {
        return cartProducts.stream().map(this::toDto).toList();
    }

    @Override
    public List<CartProduct> toEntities(final List<CartProductDTO> dTOs) {
        return dTOs.stream().map(this::toEntity).toList();
    }

    @Override
    public CartProduct toEntity(final CartProductDTO cartProductDTO) {
        CartProductId cartProductId =
                new CartProductId(cartProductDTO.getCartDTO().getId(), cartProductDTO.getProductDTO().getId());

        return CartProduct.builder()
                .id(cartProductId)
                .cart(new CartMapper().toEntity(cartProductDTO.getCartDTO()))
                .quantity(cartProductDTO.getQuantity())
                .product(new ProductMapper().toEntity(cartProductDTO.getProductDTO()))
                .build();
    }
}
