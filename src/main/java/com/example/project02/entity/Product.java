package com.example.project02.entity;

import com.example.project02.exception.OutOfStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", unique = true)
    private String name;

    private double price;

    private Integer stockQuantity;

    public void removeStock(int quantity) {
        int stock = stockQuantity - quantity;
        if (stock < 0) {
            throw new OutOfStockException("재고가 부족합니다.");
        } else {
            stockQuantity = stock;
        }
    }



}
