package com.xcale.xcaletest.service.impl;

import com.xcale.xcaletest.mapper.CartProductMapper;
import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.CartProductDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.repository.CartProductRepository;
import com.xcale.xcaletest.service.ICartProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service for managing cart products.
 * This service provides methods to create cart products.
 */
@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements ICartProductService {
    private final CartProductRepository cartProductRepository;

    @Override
    public void createCartProduct(CartDTO cartDTO, ProductDTO productDTO, Integer quantity) {
        CartProductDTO cartProductDTO = CartProductDTO.builder()
                .cartDTO(cartDTO)
                .productDTO(productDTO)
                .quantity(quantity)
                .build();

        cartProductRepository.save(new CartProductMapper().toEntity(cartProductDTO));
    }
}
