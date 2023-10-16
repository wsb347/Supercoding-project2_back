package com.example.project02.service;

import com.example.project02.entity.Product;
import com.example.project02.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindService {

    private final ProductRepository productRepository;

//    조회수
    public void clickCount (String name){
        Optional<Product> clickProductName = findByName(name);

        if(clickProductName.isPresent()) {
            int clickCount = clickProductName.get().getClick();
            clickCount++;
            clickProductName.get().setClick(clickCount);
            productRepository.save(clickProductName.get());
        }
    }

//  findByName - name 검증
    public Optional<Product> findByName(String name){
        try {
            return productRepository.findByName(name);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
