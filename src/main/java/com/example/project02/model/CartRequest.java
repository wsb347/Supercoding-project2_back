package com.example.project02.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartRequest {

    private Long productId;
    private int quantity;

}
