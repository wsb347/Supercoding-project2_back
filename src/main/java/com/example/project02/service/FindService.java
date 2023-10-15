package com.example.project02.service;

import com.example.project02.entity.Product;
import com.example.project02.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindService {

    @Autowired
    ProductRepository productRepository;


//    중복 제거 Service - stockQuantity를 받아 중복 및 재고 체크
    public List<Product> distinctProduct(Integer stockQuantity){
        List<String> distinctRemainProductName = productRepository.findByStockQuantityGreaterThan(stockQuantity);

        if(distinctRemainProductName.isEmpty())
            return null;
        else{
            List<Product> distinctRemainProducts = new ArrayList<>();

            System.out.println("이름 추출 : "+distinctRemainProductName);
            for(int i=0; i<distinctRemainProductName.size(); i++) {

                List<Product> distinctRemainProduct = productRepository.findByName(distinctRemainProductName.get(i));
                if(distinctRemainProduct.size() >= 2)
                    distinctRemainProducts.add(distinctRemainProduct.get(0));
                else
                    distinctRemainProducts.addAll(distinctRemainProduct);
            }

            System.out.println("이름 추출 : "+ distinctRemainProducts);

            return distinctRemainProducts;
        }
    }


//    조회수
    public void clickCount(String name){
        List<Product> clickProductName = productRepository.findByName(name);
        for(int i=0; i<clickProductName.size(); i++){
            int clickCount = clickProductName.get(i).getClick();
            clickCount++;
            clickProductName.get(i).setClick(clickCount);
            productRepository.save(clickProductName.get(i));
        }
    }
}
