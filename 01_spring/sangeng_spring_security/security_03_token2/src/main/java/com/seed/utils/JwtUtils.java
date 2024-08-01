package com.seed.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成jwt
     *
     * @return
     */
    public String genJwt(String subject) {
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, key).setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)).compact();
        return token;
    }

    public String parseJwt(String token) {
        String subject = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        return subject;
    }

}
