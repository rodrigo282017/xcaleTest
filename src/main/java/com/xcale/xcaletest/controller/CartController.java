package com.xcale.xcaletest.controller;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;
import com.xcale.xcaletest.model.api.UpdateCartDTO;
import com.xcale.xcaletest.service.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing carts.
 * This controller provides endpoints for creating, updating, retrieving, and deleting carts.
 * Endpoints:
 * - POST /api/v1/carts - Create a new cart
 * - DELETE /api/v1/carts/{id} - Delete a cart by its ID
 * - POST /api/v1/carts/{id} - Add products to a cart by its ID
 * - GET /api/v1/carts/{id} - Get details of a cart by its ID
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final ICartService cartService;

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@RequestBody List<ProductWithQuantityDTO> productWithQuantityDTOS) {
        log.info("Received request to create a cart.");
        CartDTO createdCart = cartService.createCart(productWithQuantityDTOS);

        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        log.info("Received request to delete a cart.");
        cartService.deleteCart(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CartDTO> addCartProducts(@PathVariable String id, @RequestBody UpdateCartDTO updateCartDTO) {
        log.info("Received request to add products to a cart.");
        CartDTO updatedProduct = cartService.updateCart(id, updateCartDTO);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable String id) {
        log.info("Received request to add products to a cart.");

        return ResponseEntity.ok(cartService.getCartById(id));
    }
}
