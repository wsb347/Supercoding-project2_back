package com.example.project02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private double price;
    private Integer quantity;

}