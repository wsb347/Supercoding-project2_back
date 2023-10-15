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

//    조회수
    public void clickCount (String name){
        List<Product> clickProductName = productRepository.findByName(name);
        for (int i = 0; i < clickProductName.size(); i++) {
            int clickCount = clickProductName.get(i).getClick();
            clickCount++;
            clickProductName.get(i).setClick(clickCount);
            productRepository.save(clickProductName.get(i));
        }
    }

}
