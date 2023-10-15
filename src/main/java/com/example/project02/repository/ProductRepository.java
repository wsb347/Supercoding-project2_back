package com.example.project02.repository;

import com.example.project02.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    Optional<Product> findByName(String name);

    List<Product> findByName(String name);

    @Query("SELECT DISTINCT p.name FROM Product p WHERE p.stockQuantity > :stockQuantity")
    List<String> findByStockQuantityGreaterThan(@Param("stockQuantity") Integer stockQuantity);

//@Query("SELECT DISTINCT p FROM Product p WHERE p.stockQuantity > :stockQuantity")
//List<Product> findByStockQuantityGreaterThan(@Param("stockQuantity") Integer stockQuantity);


//    @Query("SELECT s FROM ShopEntity s WHERE s.productName LIKE %:keyword%")
//    List<ShopEntity> findByProductNameContainingKeyword(@Param("keyword") String keyword);

    List<Product> findByNameContaining(String keyword);

    List<Product> findByCategoryId(Long categoryId);


}