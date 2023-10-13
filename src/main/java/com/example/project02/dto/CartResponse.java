package com.example.project02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponse {

    private Long userId;

    private int totalCount;
    private double totalPrice;

    private List<ProductResponse> productList;
}
