package com.example.project02.controller;

import com.example.project02.entity.User;
import com.example.project02.model.Request;
import com.example.project02.service.JwtTokenService;
import com.example.project02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Request request) {
        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            userService.signup(request);
            return ResponseEntity.ok("회원가입이 성공적으로 되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 가입된 정보입니다.");
    }


}
