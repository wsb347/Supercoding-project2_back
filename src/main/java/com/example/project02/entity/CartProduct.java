package com.example.project02.entity;

import com.example.project02.exception.OutOfStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CartProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;

    private double price;

    public static CartProduct createCartProduct(Cart cart, Product product, int amount) {
        if (product.getStockQuantity() < amount) {
            throw new OutOfStockException("재고부족");
        } else {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setCart(cart);
            cartProduct.setProduct(product);
            cartProduct.setAmount(amount);
            cartProduct.setPrice(product);
            return cartProduct;
        }
    }

    public void addCount(int count) {
        this.amount += count;
    }

    public void setPrice(Product product) {
        price = amount * product.getPrice();
    }


}
