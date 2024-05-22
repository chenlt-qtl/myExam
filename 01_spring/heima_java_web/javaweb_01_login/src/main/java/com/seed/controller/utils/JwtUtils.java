package com.seed.controller.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String KEY = "seed";
    /**
     * 生成jwt
     * @return
     */
    public static String genJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("abc","234");
        claims.put("id",3234234);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,KEY).addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000)).compact();
        return token;
    }

    public static Claims parseJwt(String token){
        Claims body = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        return body;
    }


    public static void main(String[] args) {
        String token = new JwtUtils().genJwt();
        System.out.println(token);
        System.out.println(new JwtUtils().parseJwt(token));
    }
}
