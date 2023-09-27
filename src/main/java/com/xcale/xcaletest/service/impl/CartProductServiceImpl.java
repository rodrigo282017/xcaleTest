package com.xcale.xcaletest.service.impl;

import com.xcale.xcaletest.mapper.CartProductMapper;
import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.CartProductDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.repository.CartProductRepository;
import com.xcale.xcaletest.service.ICartProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service for managing cart products.
 * This service provides methods to create cart products.
 */
@Slf4j
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
        log.info("Saved cart product successfully for product id: {} and cart id: {}", productDTO.getId(), cartDTO.getId());
    }
}
