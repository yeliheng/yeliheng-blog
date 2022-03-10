package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogcommon.constant.Constants;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.RedisUtils;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogcommon.utils.UUIDUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    @Autowired
    private RedisUtils redisUtils;

    //过期时间
    @Value("${token.expireTime}")
    private int expireTime;

    // 令牌密钥
    @Value("${token.secret}")
    private String secret;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 创建Token令牌
     * @param loginUser 登录的用户
     * @param rememberMe 记住我
     * @return 令牌
     */
    public String createToken(LoginUser loginUser,boolean rememberMe) {
        String uuid = UUIDUtils.generateUUID();
        if(!rememberMe) {
            setLoginUser(uuid,loginUser,expireTime);
        }else {
            setLoginUser(uuid,loginUser,10080);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.CLAIMS_KEY,uuid);
        return Jwts.builder().setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret).compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * 通过令牌获取Claims信息
     * @param token 令牌
     * @return Claims信息
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 将登录的用户写入缓存
     * @param uuid uuid
     * @param loginUser 登录的用户
     * @param expireTime 缓存过期时间
     */
    public void setLoginUser(String uuid, LoginUser loginUser,int expireTime){
        redisUtils.setCacheObject(getTokenCacheKey(uuid), loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 刷新登录的用户缓存
     * @param loginUser 登录的用户实体
     */
    public void refreshLoginUser(LoginUser loginUser) {
        String uuid = getUUID(ServletUtils.getRequest());
        redisUtils.setCacheObject(getTokenCacheKey(uuid),loginUser,expireTime,TimeUnit.MINUTES);
    }

    /**
     * 删除缓存中的用户
     * @param uuid uuid
     */
    public void deleteLoginUser(String uuid){
        redisUtils.deleteObject(getTokenCacheKey(uuid));
    }

    /**
     * 通过请求获取Token令牌
     * @param request 请求实例
     * @return 令牌
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


    /**
     * 从缓存中取出登录的用户
     * @param request 请求
     * @return 登录的用户实体
     */
    public LoginUser getLoginUser(HttpServletRequest request){
        String token = getToken(request);
        if(StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = getClaims(token);
                String uuid = claims.get(Constants.CLAIMS_KEY).toString();
                return redisUtils.getCacheObject(getTokenCacheKey(uuid));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取uuid信息
     * @param request 请求实例
     * @return uuid
     */
    public String getUUID(HttpServletRequest request) {
        String token = getToken(request);
        if(StringUtils.isNotEmpty(token)) {
            Claims claims = getClaims(token);
            return claims.get(Constants.CLAIMS_KEY).toString();
        }
        return null;
    }


    /**
     * 获取Token缓存键
     * @param key 原始键
     * @return 缓存键
     */
    public String getTokenCacheKey(String key) {
        return Constants.LOGIN_USER_KEY + key;
    }


}
