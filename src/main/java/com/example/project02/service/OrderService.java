package com.example.project02.service;

import com.example.project02.entity.*;
import com.example.project02.repository.CartRepository;
import com.example.project02.repository.OrderProductRepository;
import com.example.project02.repository.OrderRepository;
import com.example.project02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;

    public List<OrderProduct> findUserOrderProducts(Long userId) {
        return orderProductRepository.findByUserId(userId);
    }

    public List<Order> findByUserId(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Transactional
    public void order(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("조회되지 않는 회원"));

        List<CartProduct> cartProducts = user.getCart().getCartProducts();

        for (CartProduct cartProduct : cartProducts) {

            Product product = cartProduct.getProduct();

            double price = product.getPrice();
            int amount = cartProduct.getAmount();

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, price, amount);

            orderProductRepository.save(orderProduct);

            Order order = Order.createOrder(user, orderProduct);

            orderRepository.save(order);

            Payment.createPayment(user, order);
        }

        Cart cart = cartRepository.findByUserId(userId);
        cartRepository.delete(cart);
    }
}