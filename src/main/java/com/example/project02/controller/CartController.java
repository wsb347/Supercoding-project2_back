package com.example.project02.controller;

import com.example.project02.dto.*;
import com.example.project02.entity.Product;
import com.example.project02.repository.ProductRepository;
import com.example.project02.service.CartService;
import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "장바구니 상품 추가", description = "원하는 상품Id와 수량 입력")
    @PostMapping("/cart/products")
    public ResponseEntity<Map<String, String>> addProductToCart(AuthInfo authInfo, @RequestBody CartRequest request){

        cartService.addProduct(authInfo.getUserId(), request);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 상품입니다."));

        String description = String.format("%s가 %d개 장바구니에 추가되었습니다.", product.getName(), request.getQuantity());
        Map<String, String> response  = new HashMap<>();
        response.put("message", description);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니를 조회했을 때 나오는 cartProductId와 변경 원하는 수량 입력")
    @PutMapping("/cart/products")
    public ResponseEntity<String> changeProduct(AuthInfo authInfo, @RequestBody ChangeAmountRequest request) {
        cartService.changeProductAmount(authInfo.getUserId(), request);

        return ResponseEntity.ok().body("변경되었습니다.");
    }

    @Operation(summary = "장바구니 조회", description = "총금액, 총개수 및 세부사항 조회")
    @GetMapping("/carts")
    public ResponseEntity<CartResponse> getCart(AuthInfo authInfo) {
        CartResponse cart = cartService.getCart(authInfo.getUserId());
        return ResponseEntity.ok().body(cart);
    }

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니를 조회했을 때 나오는 cartProductId입력, null이면 장바구니 전체 삭제")
    @DeleteMapping("/carts")
    public ResponseEntity<String> removeProduct(AuthInfo authInfo, @RequestBody SelectProductRequest request) {
        cartService.removeProduct(authInfo.getUserId(), request);

        return ResponseEntity.ok().body("삭제 되었습니다.");

    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}