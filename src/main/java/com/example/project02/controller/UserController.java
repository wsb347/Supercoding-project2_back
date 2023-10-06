package com.example.project02.controller;

import com.example.project02.entity.User;
import com.example.project02.model.Request;
import com.example.project02.service.JwtTokenService;
import com.example.project02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Request request) {
        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            userService.signup(request);
            return ResponseEntity.ok("회원가입이 성공적으로 되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 가입된 정보입니다.");
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Request request) {
        ResponseEntity<?> errorResponse = validateUser(request);
        if (errorResponse != null) return errorResponse;

        HttpHeaders headers = jwtTokenService.createToken(request);
        return ResponseEntity.status(200).headers(headers).body("로그인되었습니다");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader String token) {
        jwtTokenService.validation(token);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }


    // 회원 정보 검증
    public ResponseEntity<?> validateUser(Request request) {
        request.setPassword(userService.sha256(request.getPassword()));
        User user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null){
            return userService.validateUser(request);
        } else if (Objects.equals(user.getStatus(), "delete"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("유효한 회원이 아닙니다.");
        return null;
    }
}
