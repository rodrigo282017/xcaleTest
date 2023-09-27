package com.xcale.xcaletest.service;

import com.xcale.xcaletest.exception.EntityNotFoundException;
import com.xcale.xcaletest.exception.ValidationException;
import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.api.ProductWithQuantityDTO;
import com.xcale.xcaletest.model.api.UpdateCartDTO;
import com.xcale.xcaletest.model.entity.Cart;
import com.xcale.xcaletest.model.entity.Product;
import com.xcale.xcaletest.repository.CartRepository;
import com.xcale.xcaletest.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private IProductService productService;

    @Mock
    private ICartProductService cartProductService;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCart_validInput_returnCartDTO() {
        List<ProductWithQuantityDTO> productWithQuantityDTOS = new ArrayList<>();

        ProductWithQuantityDTO productWithQuantityDTO = ProductWithQuantityDTO.builder()
                .productId(UUID.randomUUID())
                .quantity(2)
                .build();

        productWithQuantityDTOS.add(productWithQuantityDTO);

        ProductDTO productDTO = ProductDTO.builder()
                .id(productWithQuantityDTO.getProductId())
                .build();

        Cart cart = Cart.builder()
                .id(UUID.randomUUID())
                .products(null)
                .build();

        when(productService.getProductById(productWithQuantityDTO.getProductId().toString())).thenReturn(productDTO);
        when(cartRepository.save(any())).thenReturn(cart);

        CartDTO cartDTO = cartService.createCart(productWithQuantityDTOS);

        assertEquals(productWithQuantityDTOS.size(), cartDTO.getProducts().size());
        assertEquals(productWithQuantityDTO.getProductId(), cartDTO.getProducts().get(0).getId());

        // Verify method invocations
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(cartProductService, times(1)).createCartProduct(any(CartDTO.class), any(ProductDTO.class), anyInt());
    }

    @Test
    void createCart_emptyProductList_throwsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> cartService.createCart(new ArrayList<>()));

        assertEquals("MissingRequiredParameters", exception.getCode());
        assertEquals("Products are needed to create a cart", exception.getMessage());
    }

    @Test
    void createCart_multipleProducts_returnCartDTO() {
        // Create multiple products with quantities
        ProductWithQuantityDTO product1 = ProductWithQuantityDTO.builder()
                .productId(UUID.randomUUID())
                .quantity(2)
                .build();

        ProductWithQuantityDTO product2 = ProductWithQuantityDTO.builder()
                .productId(UUID.randomUUID())
                .quantity(3)
                .build();

        List<ProductWithQuantityDTO> productWithQuantityDTOS = Arrays.asList(product1, product2);

        // Create ProductDTO
        ProductDTO productDTO1 = ProductDTO.builder()
                .id(product1.getProductId())
                .build();

        ProductDTO productDTO2 = ProductDTO.builder()
                .id(product2.getProductId())
                .build();

        Cart cart = Cart.builder()
                .id(UUID.randomUUID())
                .products(null)
                .build();

        when(productService.getProductById(product1.getProductId().toString())).thenReturn(productDTO1);
        when(productService.getProductById(product2.getProductId().toString())).thenReturn(productDTO2);
        when(cartRepository.save(any())).thenReturn(cart);

        //Call method
        CartDTO cartDTO = cartService.createCart(productWithQuantityDTOS);

        //Assertions
        assertEquals(productWithQuantityDTOS.size(), cartDTO.getProducts().size());

        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(cartProductService, times(2)).createCartProduct(any(CartDTO.class), any(ProductDTO.class), anyInt());
    }

    @Test
    void deleteCart_validInput_successful() {
        String cartId = UUID.randomUUID().toString();
        cartService.deleteCart(cartId);

        verify(cartRepository, times(1)).deleteById(UUID.fromString(cartId));
    }

    @Test
    void updateCart_validInput_returnUpdatedCartDTO() {
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Product existentProduct =
                Product.builder()
                        .amount(BigDecimal.TEN)
                        .description("Product 1")
                        .id(productId)
                        .build();

        UpdateCartDTO updateCartDTO = UpdateCartDTO.builder()
                .productQuantity
                        (List.of(UpdateCartDTO.ProductAndQuantity
                                .builder()
                                .productId(productId)
                                .quantity(2)
                                .build()))
                .build();

        Cart cart = Cart.builder()
                .id(cartId)
                .products(List.of(existentProduct))
                .build();

        ProductDTO productDTO = ProductDTO
                .builder()
                .id(productId)
                .build();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productService.getProductById(updateCartDTO.getProductQuantity().get(0).getProductId().toString())).thenReturn(productDTO);
        when(cartRepository.save(cart)).thenReturn(cart);

        //Call service
        CartDTO updatedCartDTO = cartService.updateCart(cartId.toString(), updateCartDTO);

        // Assertions
        assertEquals(cartId, updatedCartDTO.getId());
        assertEquals(updateCartDTO.getProductQuantity().size(), updatedCartDTO.getProducts().size());

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartProductService, times(1)).createCartProduct(any(CartDTO.class), any(ProductDTO.class), anyInt());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void getCartById_validInput_successful() {
        String cartId = UUID.randomUUID().toString();
        Cart cart = new Cart();
        when(cartRepository.findById(UUID.fromString(cartId))).thenReturn(java.util.Optional.of(cart));

        CartDTO cartDTO = cartService.getCartById(cartId);

        assertNotNull(cartDTO);
    }

    @Test
    void getCartById_invalidInput_entityNotFoundException() {
        String cartId = UUID.randomUUID().toString();

        when(cartRepository.findById(UUID.fromString(cartId))).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cartService.getCartById(cartId));

        assertEquals("Cart not found", exception.getCode());
        assertEquals("Could not find cart.", exception.getMessage());
    }

    @Test
    void findInactiveCarts_validInput_successful() {
        Instant time = Instant.now();
        List<Cart> cartList = new ArrayList<>();
        when(cartRepository.findByUpdatedAtBefore(time)).thenReturn(cartList);

        List<CartDTO> cartDTOList = cartService.findInactiveCarts(time);

        assertNotNull(cartDTOList);
    }
}
