package com.xcale.xcaletest.service;

import com.xcale.xcaletest.exception.EntityNotFoundException;
import com.xcale.xcaletest.exception.ValidationException;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.entity.Product;
import com.xcale.xcaletest.repository.ProductRepository;
import com.xcale.xcaletest.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void createProduct_ValidProductDTO_ReturnsProductDTO() {
        ProductDTO productDTO = ProductDTO.builder()
                .description("Product 1")
                .amount(BigDecimal.TEN)
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        ProductDTO createdProduct = productService.createProduct(productDTO);

        assertEquals(productDTO.getId(), createdProduct.getId());
        assertEquals(productDTO.getDescription(), createdProduct.getDescription());
        assertEquals(productDTO.getAmount(), createdProduct.getAmount());
    }

    @Test
    void createProduct_InvalidProductDTO_ThrowsValidationException() {
        ProductDTO productDTO = ProductDTO.builder().build();

        ValidationException exception = assertThrows(ValidationException.class, () -> productService.createProduct(productDTO));
        assertEquals("MissingRequiredParameters", exception.getCode());
        assertEquals("Description and amount are required", exception.getMessage());

    }

    @Test
    void getProductById_ExistingProductId_ReturnsProductDTO() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(new Product()));

        ProductDTO productDTO = productService.getProductById(productId.toString());

        assertNotNull(productDTO);
    }

    @Test
    void getProductById_NonExistingProductId_ThrowsEntityNotFoundException() {
        String nonExistingId = UUID.randomUUID().toString();
        when(productRepository.findById(UUID.fromString(nonExistingId))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(nonExistingId));
    }

    @Test
    void updateProduct_InvalidProductDTO_ThrowsValidationException() {
        UUID productId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.builder()
                .description("Updated Product Name")
                .amount(BigDecimal.ONE)
                .build();

        EntityNotFoundException exception = assertThrows
                (EntityNotFoundException.class, () -> productService.updateProduct(productId.toString(), productDTO));

        assertEquals("Product not found", exception.getCode());
        assertEquals("Could not find product.", exception.getMessage());
    }

    @Test
    void updateProduct_ValidProductDTO_ReturnsUpdatedProductDTO() {
        UUID productId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.builder()
                .description("Updated Product Name")
                .amount(BigDecimal.valueOf(20.0))
                .build();

        Product existingProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductDTO updatedProduct = productService.updateProduct(productId.toString(), productDTO);

        assertEquals(productDTO.getId(), updatedProduct.getId());
        assertEquals(productDTO.getAmount(), updatedProduct.getAmount());
        assertEquals(productDTO.getDescription(), updatedProduct.getDescription());
    }

    @Test
    void deleteProduct_ExistingProductId_DeletesProduct() {
        UUID productId = UUID.randomUUID();

        productService.deleteProduct(productId.toString());

        verify(productRepository, times(1)).deleteById(productId);
    }
}
