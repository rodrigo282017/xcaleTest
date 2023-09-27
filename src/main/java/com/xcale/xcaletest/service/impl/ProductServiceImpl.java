package com.xcale.xcaletest.service.impl;

import com.xcale.xcaletest.exception.EntityNotFoundException;
import com.xcale.xcaletest.exception.ValidationException;
import com.xcale.xcaletest.mapper.ProductMapper;
import com.xcale.xcaletest.model.api.ProductDTO;
import com.xcale.xcaletest.model.entity.Product;
import com.xcale.xcaletest.repository.ProductRepository;
import com.xcale.xcaletest.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

/**
 * Implementation of the product service.
 * This service provides methods to create, update, retrieve, and delete products, as well as retrieve all products.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        log.info("Retrieved all products successfully");

        return new ProductMapper().toDTOs(productList);
    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Product not found", "Could not find product.", id));
        log.info("Get product by id successfully for id: {}", id);

        return new ProductMapper().toDto(product);
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        validateRequest(productDTO);

        Product product = productRepository.save(new ProductMapper().toEntity(productDTO));
        productDTO.setId(product.getId());
        log.info("Created product successfully for product id: {}", productDTO.getId());

        return productDTO;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product not found", "Could not find product.", id);
        }

        Product product = optionalProduct.get();

        if (productDTO.getAmount() != null) {
            product.setAmount(productDTO.getAmount());
        }

        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }

        productRepository.save(product);
        log.info("Updated product successfully for product id: {}", productDTO.getId());

        return new ProductMapper().toDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        productRepository.deleteById(UUID.fromString(id));
        log.info("Deleted product successfully for product id: {}", id);

    }

    private void validateRequest(ProductDTO productDTO) {
        if (!hasText(productDTO.getDescription()) || productDTO.getAmount() == null) {
            log.info("Missing required parameters for product id: {}", productDTO.getId());
            throw new ValidationException(
                    "MissingRequiredParameters",
                    "Description and amount are required",
                    productDTO.getDescription(),
                    productDTO.getAmount()
            );
        }
    }
}
