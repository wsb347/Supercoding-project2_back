package com.example.project02.converter;

import com.example.project02.entity.User;
import com.example.project02.dto.Request;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserConverter {
    public User toEntity(Request response) {
        return User.builder()
                .email(response.getEmail())
                .address(response.getAddress())
                .phone(response.getPhone())
                .password(response.getPassword())
                .status("REGISTERED")
                .gender(response.getGender())
                .img("https://www.pngkey.com/png/detail/121-1219231_user-default-profile.png")
                .build();
    }


}
