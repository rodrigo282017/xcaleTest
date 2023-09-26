package com.xcale.xcaletest.mapper;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.entity.Cart;
import com.xcale.xcaletest.util.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper implements Mapper<CartDTO, Cart> {

    @Override
    public CartDTO toDto(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .products(cart.getProducts() != null ? new ProductMapper().toDTOs(cart.getProducts()) : null)
                .build();
    }

    @Override
    public List<CartDTO> toDTOs(List<Cart> carts) {
        return carts.stream().map(this::toDto).toList();
    }

    @Override
    public List<Cart> toEntities(List<CartDTO> dTOs) {
        return null;
    }

    @Override
    public Cart toEntity(CartDTO cartDTO) {
        return Cart.builder()
                .id(cartDTO.getId())
                .products(cartDTO.getProducts() != null ? new ProductMapper().toEntities(cartDTO.getProducts()) : null)
                .build();
    }
}
