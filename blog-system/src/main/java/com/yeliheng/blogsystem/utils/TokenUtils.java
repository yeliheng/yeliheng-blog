package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogsystem.entity.LoginUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    @Autowired
    private RedisUtils redisUtils;

    //过期时间
    @Value("${token.expireTime}")
    private long expireTime;

    // 令牌密钥
    @Value("${token.secret}")
    private String secret;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    public String createToken(LoginUser loginUser) {
        setLoginUser(loginUser);
        return Jwts.builder().setSubject(String.format("%s", loginUser.getUsername()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * MILLIS_MINUTE))
                .signWith(SignatureAlgorithm.HS512, secret).compressWith(CompressionCodecs.GZIP).compact();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.warn("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.warn("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.warn("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.warn("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.warn("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public String getUsername(String token){
       return getClaims(token).getSubject();
    }

    public void setLoginUser(LoginUser loginUser){
        redisUtils.setCacheObject(loginUser.getUser().getId().toString(),loginUser);
    }


    public LoginUser getLoginUser(Long userId){
        return redisUtils.getCacheObject(userId.toString());
    }


}
