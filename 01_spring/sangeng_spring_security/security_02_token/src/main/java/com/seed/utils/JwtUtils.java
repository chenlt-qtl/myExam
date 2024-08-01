package com.seed.utils;

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
    public static String genJwt(String subject){
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,KEY).setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000)).compact();
        return token;
    }

    public static String parseJwt(String token){
        String subject = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getSubject();
        return subject;
    }

}
