package com.xcale.xcaletest.controller;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;
import com.xcale.xcaletest.service.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing carts.
 * This controller handles creating a new cart.
 *
 * Endpoints:
 * - POST /api/v1/carts - Create a new cart
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
}
