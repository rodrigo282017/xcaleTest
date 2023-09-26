package com.xcale.xcaletest.service.impl;

import com.xcale.xcaletest.exception.EntityNotFoundException;
import com.xcale.xcaletest.exception.ValidationException;
import com.xcale.xcaletest.mapper.CartMapper;
import com.xcale.xcaletest.mapper.ProductMapper;
import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;
import com.xcale.xcaletest.model.api.UpdateCartDTO;
import com.xcale.xcaletest.model.entity.Cart;
import com.xcale.xcaletest.repository.CartRepository;
import com.xcale.xcaletest.service.ICartProductService;
import com.xcale.xcaletest.service.ICartService;
import com.xcale.xcaletest.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the cart service.
 * This service provides methods to create, update, retrieve, and delete carts, as well as find inactive carts.
 */
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

    @Override
    @Transactional
    public void deleteCart(String id) {
        //This method is going to delete the cart repository entity and the associated cartProduct
        cartRepository.deleteById(UUID.fromString(id));
    }

    @Override
    @Transactional
    public CartDTO updateCart(String id, UpdateCartDTO updateCartDTO) {
        UUID cartId = UUID.fromString(id);

        // Find the cart created
        Cart cart = cartRepository
                .findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found", "Could not find cart.", id));

        // Iterate in the cart created, if there is a new product save if not update
        for (UpdateCartDTO.ProductAndQuantity productAndQuantity : updateCartDTO.getProductQuantity()) {
            UUID productId = productAndQuantity.getProductId();
            int quantity = productAndQuantity.getQuantity();

            ProductDTO productDTO = productService.getProductById(productId.toString());
            cartProductService.createCartProduct(new CartMapper().toDto(cart), productDTO, quantity);

        }

        cart = cartRepository.save(cart);

        CartDTO updatedCartDTO = new CartMapper().toDto(cart);
        updatedCartDTO.setProducts(new ProductMapper().toDTOs(cart.getProducts()));

        return updatedCartDTO;
    }

    @Override
    public CartDTO getCartById(String id) {
        Cart cart = cartRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Cart not found", "Could not find cart.", id));

        return new CartMapper().toDto(cart);
    }

    @Override
    @Transactional
    public List<CartDTO> findInactiveCarts(Instant time) {
        List<Cart> cartList = cartRepository.findByUpdatedAtBefore(time);

        return new CartMapper().toDTOs(cartList);
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
