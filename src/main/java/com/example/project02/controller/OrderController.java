package com.example.project02.controller;

import com.example.project02.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/users"})
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping({"/{userId}/payments"})
    public ResponseEntity<String> order(@PathVariable("userId") Long userId) {
        this.orderService.order(userId);
        return ResponseEntity.ok().body("성공~");
    }

}
