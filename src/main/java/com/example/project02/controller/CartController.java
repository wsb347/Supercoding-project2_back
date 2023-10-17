package com.example.project02.controller;

import com.example.project02.dto.UserRequest;
import com.example.project02.entity.Cart;
import com.example.project02.entity.CartProduct;
import com.example.project02.entity.Product;
import com.example.project02.dto.CartRequest;
import com.example.project02.entity.User;
import com.example.project02.repository.ProductRepository;
import com.example.project02.service.CartService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.project02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class CartController {

    private final UserService userService;
    private final CartService cartService;
    private final ProductRepository productRepository;

    // 장바구니
    @GetMapping("/cart/{id}")
    public String cartPage(@PathVariable("id") Long id, Model model, UserRequest userRequest, HttpSession httpSession) {
        User user = userService.findUser(id);
        Cart userCart = user.getCart();

        if (userCart != null) {
            if (user.getId().equals(id)) {
                List<CartProduct> cartProductList = cartService.allUserCartView(userCart);

                int totalPrice = 0;
                for (CartProduct cartProduct : cartProductList) {
                    totalPrice += cartProduct.getCount() * cartProduct.getProduct().getPrice();
                }

                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("cartProducts", cartProductList);
                httpSession.setAttribute("user", userService.findUser(id));

                return "/product/cart";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/{userId}/cart/products")
    public ResponseEntity<Map<String, String>> addProductToCart(@PathVariable("userId") Long userId, @RequestBody CartRequest request) {

        cartService.addProduct(userId, request);

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() ->
                new RuntimeException("등록되지 않은 상품입니다."));

        String description = String.format("%s가 %d개 장바구니에 추가되었습니다.", product.getName(), request.getQuantity());
        Map<String, String> response  = new HashMap<>();
        response.put("message", description);

        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}