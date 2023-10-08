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
                .img("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
                .build();
    }


}
