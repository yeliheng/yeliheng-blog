package com.yeliheng.blogsystem.utils;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    //过期时间
    @Value("${token.expireTime}")
    private static long expireTime;

    // 令牌密钥
    @Value("${token.secret}")
    private String secret;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    public String createToken(User user){
        return Jwts.builder().setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * MILLIS_MINUTE))
                .signWith(SignatureAlgorithm.HS512,secret).compressWith(CompressionCodecs.GZIP).compact();
    }

}
