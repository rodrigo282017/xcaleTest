package com.xcale.xcaletest.service;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;

import java.util.List;

public interface ICartService {
    CartDTO createCart(List<ProductWithQuantityDTO> createCartRequestDTO);
}
