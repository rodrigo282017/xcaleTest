package com.xcale.xcaletest.service;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.repository.CartProductRepository;
import com.xcale.xcaletest.service.impl.CartProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CartProductServiceImplTest {

    @Mock
    private CartProductRepository cartProductRepository;

    private CartProductServiceImpl cartProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartProductService = new CartProductServiceImpl(cartProductRepository);
    }

    @Test
    public void createCartProduct_ShouldSaveToRepository() {
        CartDTO cartDTO = CartDTO.builder().id(java.util.UUID.randomUUID()).build();
        ProductDTO productDTO = ProductDTO.builder()
                .id(java.util.UUID.randomUUID())
                .description("Product 1")
                .amount(BigDecimal.valueOf(10.0))
                .build();
        Integer quantity = 2;

        // Act
        cartProductService.createCartProduct(cartDTO, productDTO, quantity);

        // Assert
        verify(cartProductRepository, times(1)).save(any());
    }
}
