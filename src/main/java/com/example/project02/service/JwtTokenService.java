package com.example.project02.service;

import com.example.project02.entity.User;
import com.example.project02.model.Request;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretkey;

    @Value("${access-token.plus-hour}")
    private long plusHour;

    // 토큰값 생성
    public String create(Map<String, Object> claims){
        var key = Keys.hmacShaKeyFor(secretkey.getBytes());
        LocalDateTime expireAt = LocalDateTime.now().plusHours(plusHour);
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expireAt)
                .compact();
    }


    // 생성한 코드 로그인 시 넘겨줌
    public HttpHeaders createToken(Request request) {
        User user = userService.findByEmail(request.getEmail());
        var claims = new HashMap<String, Object>();
        claims.put("user_id", user.getId());
        String token = create(claims);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}
