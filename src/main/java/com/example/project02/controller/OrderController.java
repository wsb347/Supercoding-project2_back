package com.example.project02.controller;

import com.example.project02.dto.SelectProductRequest;
import com.example.project02.entity.Cart;
import com.example.project02.repository.CartRepository;
import com.example.project02.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartRepository cartRepository;

    @PostMapping("/{userId}/payments")
    public ResponseEntity<Map<String, String>> order(@PathVariable("userId") Long userId, SelectProductRequest request) {

       orderService.order(userId, request);

        String response = "결제 되었습니다";

        Map<String, String> message = new HashMap<>();
        message.put("message", response);

        return ResponseEntity.ok().body(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
