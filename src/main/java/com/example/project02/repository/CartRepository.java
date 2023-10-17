package com.example.project02.repository;

import com.example.project02.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

    @Query("SELECT SUM(cp.price) FROM CartProduct cp WHERE cp.cart.id = :cartId")
    Double calculateTotalPriceByCartId(@Param("cartId") Long cartId);
}
