package com.xcale.xcaletest.service.impl;

import com.xcale.xcaletest.exception.EntityNotFoundException;
import com.xcale.xcaletest.mapper.CartProductMapper;
import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.CartProductDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.entity.CartProduct;
import com.xcale.xcaletest.repository.CartProductRepository;
import com.xcale.xcaletest.service.ICartProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<CartProductDTO> getCartProducts(String cartId, String productId) {
        //Check first if the product exists
        List<CartProduct> cartProducts = cartProductRepository.findAllByCartId(UUID.fromString(cartId));

        return new CartProductMapper().toDTOs(cartProducts);
    }
}
