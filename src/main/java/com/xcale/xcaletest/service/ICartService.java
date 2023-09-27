package com.xcale.xcaletest.service;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;
import com.xcale.xcaletest.model.api.UpdateCartDTO;

import java.time.Instant;
import java.util.List;

public interface ICartService {
    CartDTO createCart(List<ProductWithQuantityDTO> createCartRequestDTO);

    void deleteCart(String id);

    CartDTO updateCart(String id, UpdateCartDTO updateCartDTO);

    CartDTO getCartById(String id);

    List<CartDTO> findInactiveCarts(Instant threshold);
}
