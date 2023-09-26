package com.xcale.xcaletest.service;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.CartProductDTO;
import com.xcale.xcaletest.model.api.ProductDTO;

import java.util.List;

public interface ICartProductService {
    void createCartProduct(CartDTO cartDTO, ProductDTO productDTO, Integer quantity);

    List<CartProductDTO> getCartProducts(String cartId, String productId);
}
