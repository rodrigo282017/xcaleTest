package com.xcale.xcaletest.service.impl;

import com.xcale.xcaletest.exception.ValidationException;
import com.xcale.xcaletest.mapper.CartMapper;
import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;
import com.xcale.xcaletest.model.entity.Cart;
import com.xcale.xcaletest.repository.CartRepository;
import com.xcale.xcaletest.service.ICartProductService;
import com.xcale.xcaletest.service.ICartService;
import com.xcale.xcaletest.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartProductService cartProductService;

    @Override
    public CartDTO createCart(List<ProductWithQuantityDTO> productWithQuantityDTOS) {
        validateRequest(productWithQuantityDTOS);

        //First we create an empty cart
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        CartDTO cartDTO = new CartMapper().toDto(cart);

        //This list is to show the dto result
        List<ProductDTO> productDTOList = new ArrayList<>();

        //Now we add the products with quantities to the cart
        for (ProductWithQuantityDTO productWithQuantity :
                productWithQuantityDTOS) {
            //Check if the products exist, otherwise EntityNotFound will be thrown
            ProductDTO productDTO = productService.getProductById(productWithQuantity.getProductId().toString());
            productDTOList.add(productDTO);

            //We need to associate the cart and the product
            cartProductService.createCartProduct(cartDTO, productDTO, productWithQuantity.getQuantity());
        }

        cartDTO.setProducts(productDTOList);

        return cartDTO;
    }

    private void validateRequest(List<ProductWithQuantityDTO> productWithQuantityDTOS) {
        if (productWithQuantityDTOS.isEmpty())
            throw new ValidationException(
                    "MissingRequiredParameters",
                    "Products are needed to create a cart",
                    productWithQuantityDTOS
            );
    }
}
