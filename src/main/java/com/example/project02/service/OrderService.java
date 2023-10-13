package com.example.project02.service;

import com.example.project02.entity.*;
import com.example.project02.repository.CartRepository;
import com.example.project02.repository.OrderProductRepository;
import com.example.project02.repository.OrderRepository;
import com.example.project02.repository.UserRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public void order(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("조회되지 않는 회원"));

        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("조회 불가"));

        cart.getCartProducts()
                .stream()
                .map(cartProduct -> {
                    Product product = cartProduct.getProduct();
                    double price = product.getPrice();
                    int amount = cartProduct.getAmount();
                    OrderProduct orderProduct = OrderProduct.createOrderProduct(product, price, amount);
                    orderProductRepository.save(orderProduct);

                    return Order.createOrder(user, orderProduct);
                })
                .forEach(order -> {
                    orderRepository.save(order);
                    Payment.createPayment(user, order);
                });


        cartRepository.delete(cart);
    }
}



