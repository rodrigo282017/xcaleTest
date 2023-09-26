package com.xcale.xcaletest.repository;

import com.xcale.xcaletest.model.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, UUID> {
    List<CartProduct> findAllByCartId(UUID cartId);
}
