package com.example.project02.service;

import com.example.project02.converter.UserConverter;
import com.example.project02.entity.User;
import com.example.project02.model.Request;
import com.example.project02.repository.UserRopository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRopository userRopository;
    UserConverter userConverter = new UserConverter();

    public User findByEmail(String email) {
        var isUser = userRopository.findByEmail(email);
        return isUser.orElse(null);
    }

    public void signup(Request request) {
        isValidPassword(request.getPassword());
        request.setPassword(request.getPassword());
        User NewUser = userConverter.toEntity(request);

        userRopository.save(NewUser);
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
}
