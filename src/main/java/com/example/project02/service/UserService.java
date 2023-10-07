package com.example.project02.service;

import com.example.project02.converter.UserConverter;
import com.example.project02.entity.User;
import com.example.project02.model.Request;
import com.example.project02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    UserConverter userConverter = new UserConverter();

    public User findByEmail(String email) {
        var isUser = userRepository.findByEmail(email);
        return isUser.orElse(null);
    }

    public void signup(Request request) {
        isValidPassword(request.getPassword());
        request.setPassword(sha256(request.getPassword()));
        User NewUser = userConverter.toEntity(request);

        userRepository.save(NewUser);
    }

    // 회원가입시 비밀번호 검증
    public void isValidPassword(String password) {
        // 비밀번호 길이 체크 (8 ~ 20 자)
        if (password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 8~20자 사이여야 합니다.");
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        // 영문자와 숫자의 조합인지 체크
        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) {
                hasLetter = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            }
        }

        if (!hasLetter || !hasDigit) {
            throw new IllegalArgumentException("비밀번호는 영문자와 숫자의 조합이어야 합니다.");
        }
    }

    public String sha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            try (Formatter formatter = new Formatter()) {
                for (byte b : hash) {
                    formatter.format("%02x", b);
                }
                return formatter.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public User findByEmailAndPassword(String email, String password) {
        var isUser = userRepository.findFirstByEmailAndPasswordOrderByIdDesc(email, password);
        return isUser.orElse(null);
    }

    public ResponseEntity<?> validateUser(Request request) {
        if (findByEmail(request.getEmail()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("가입된 이메일이 아닙니다.");
        else if (findByEmailAndPassword(request.getEmail(), request.getPassword()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 비밀번호 입니다.");

        return null;
    }

    public void withdrawal(Request request) {
        var existingUser = userRepository.findByEmailAndStatus(request.getEmail(), "REGISTERED");
        if (existingUser.isPresent()) {
            User deleteUser = existingUser.get();
            deleteUser.setStatus("delete");
            userRepository.save(deleteUser);
        }
    }
}
