package com.example.project02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private double productPrice;
    private double totalPrice;
    private int amount;
    private Integer stockQuantity;

}
