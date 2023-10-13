package com.example.project02.controller;

import com.example.project02.entity.Category;
import com.example.project02.entity.Product;
import com.example.project02.repository.CategoryRepository;
import com.example.project02.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class FindController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;



    //    §§§ 쇼핑몰 전체 물품 조회 §§§
//    전체 페이지에서 현재 가지고 있는 물품을 모두 보여줌
//    JPA Repository findFirstByStockQuantityGreaterThan(n) = n보다 큰 값만 찾아 출력 그 중 첫번째 값(중복 제외) - 옵션 중복 조회 방지
        @GetMapping("/all")
        public Optional<Product> allProduct() {
                return productRepository.findFirstByStockQuantityGreaterThan(0);
        }







    //§§§ 쇼핑몰 상세 물품 조회 §§§
//
//            | 각 쇼핑몰 상품별 이미지, 상품명, 옵션 가격을 나타낸다. |
//
//            - Controller : GetMapping (Path(id)) = id 값을 받아와 해당 id 값의 정보 출력
//
//- JPA Repository getById(id)
//
//    file 불러오기 -
//
        @GetMapping("/detail/{name}")
        public Optional<Product> detailProduct(@PathVariable String name){
            return productRepository.findByName(name);
        }



//            (선택) 쇼핑물 특정 물품명이나 카테고리로 검색 |
//
//            | 카테고리나 특정 물품명을 검색하여 조회할 수 있음. |
//
//            1. 재고가 없는 물품을 볼 수도 있음.

//             /api/search/{물품명}|{카테고리}
//             /api/search?카테고리=[]
//
//            - Controller : GetMapping (Parm(물품명))
//            - Controller : GetMapping (Parm(카테고리))
//
//             - JPA Repository getById(id)
//             Containing : keyword를 포함한 결과값 반환

        @GetMapping("/search/keyword")
        public List<Product> searchKeyword(@RequestParam String keyword){
            return productRepository.findByNameContaining(keyword);
        }

        @GetMapping("/search/categoryName")
        public List<Product> searchCategory(@RequestParam String categoryName){
            Long categoryId = categoryRepository.findByCategoryId(categoryName);

             return productRepository.findByCategoryId(categoryId);
        }


//        카테고리 이름과 id 조회
        @GetMapping("/category")
        public List<Category> allCategory(){
            return categoryRepository.findAll();
        }




//            ( 선택 ) 쇼핑몰 특정 조건 배열 조회
//            배열 등 추가 기능 구현
//            1. 조회수
//            2. 구매수 - 구매 파트에서 결제 시 count 한 후 product entity에 추가
//
//            3. 인기순 - 물품에 좋아요 기능 추가 후



}