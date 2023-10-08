package com.example.project02.repository;

import com.example.project02.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);

    @Query("select coalesce(cp.count, 0) from CartProduct cp where cp.cart.id = :cartId and cp.product.id = :productId")
    Integer findCount(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
