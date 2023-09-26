package com.xcale.xcaletest.service;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductDTO;

public interface ICartProductService {
    void createCartProduct(CartDTO cartDTO, ProductDTO productDTO, Integer quantity);
}
