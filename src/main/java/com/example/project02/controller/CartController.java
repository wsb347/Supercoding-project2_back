package com.example.project02.controller;

import com.example.project02.dto.CartResponse;
import com.example.project02.dto.ChangeAmountRequest;
import com.example.project02.dto.SelectProductRequest;
import com.example.project02.entity.Product;
import com.example.project02.dto.CartRequest;
import com.example.project02.repository.ProductRepository;
import com.example.project02.service.CartService;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    @PostMapping("/{userId}/cart/products")
    public ResponseEntity<Map<String, String>> addProductToCart(@PathVariable("userId") Long userId, @RequestBody CartRequest request) {

        cartService.addProduct(userId, request);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 상품입니다."));

        String description = String.format("%s가 %d개 장바구니에 추가되었습니다.", product.getName(), request.getQuantity());
        Map<String, String> response  = new HashMap<>();
        response.put("message", description);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{userId}/cart/products")
    public ResponseEntity<String> changeProduct(@PathVariable("userId")Long userId, @RequestBody ChangeAmountRequest request) {
        cartService.changeProductAmount(userId, request);

        return ResponseEntity.ok().body("변경되었습니다.");
    }

    @GetMapping("/{userId}/carts")
    public ResponseEntity<CartResponse> getCart(@PathVariable("userId") Long userId) {
        CartResponse cart = cartService.getCart(userId);
        return ResponseEntity.ok().body(cart);
    }

    @DeleteMapping("{userId}/carts")
    public ResponseEntity<String> removeProduct(@PathVariable("userId") Long userId, @RequestBody SelectProductRequest request) {
        cartService.removeProduct(userId, request);

        return ResponseEntity.ok().body("삭제 되었습니다.");

    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}