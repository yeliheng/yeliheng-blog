package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogcommon.constant.Constants;
import com.yeliheng.blogcommon.utils.RedisUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogcommon.utils.UUIDUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.service.IUserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IUserService userService;

    //过期时间
    @Value("${token.expireTime}")
    private long expireTime;

    // 令牌密钥
    @Value("${token.secret}")
    private String secret;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    public String createToken(LoginUser loginUser) {
        String uuid = UUIDUtils.generateUUID();
        setLoginUser(uuid,loginUser);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.CLAIMS_KEY,uuid);
        return Jwts.builder().setClaims(claims)
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
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    public void setLoginUser(String uuid, LoginUser loginUser){
        redisUtils.setCacheObject(uuid, loginUser, (int) expireTime, TimeUnit.MINUTES);
    }

    public void deleteLoginUser(String uuid){
        redisUtils.deleteObject(uuid);
    }

    /**
     * 获取请求token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }


    public LoginUser getLoginUser(HttpServletRequest request){
        String token = getToken(request);
        if(StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = getClaims(token);
                String uuid = claims.get(Constants.CLAIMS_KEY).toString();
                return redisUtils.getCacheObject(uuid);
            } catch (Exception ignored) {}
        }
        return null;
    }

    public String getUUID(HttpServletRequest request) {
        String token = getToken(request);
        if(StringUtils.isNotEmpty(token)) {
            Claims claims = getClaims(token);
            return claims.get(Constants.CLAIMS_KEY).toString();
        }
        return null;
    }


}
