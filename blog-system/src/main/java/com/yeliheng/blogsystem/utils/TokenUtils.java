package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogcommon.constant.Constants;
import com.yeliheng.blogcommon.utils.RedisUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
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

    public void deleteLoginUser(Long userId){
        redisUtils.deleteObject(userId.toString());
    }

    /**
     *
     * 获取缓存的用户信息(没有则代表凭据过期)
     * @param userId 用户id
     * @return 用户信息
     */

    public LoginUser getLoginUser(Long userId){
            return redisUtils.getCacheObject(userId.toString());
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
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
        LoginUser loginUser = redisUtils.getCacheObject(userService.selectUidByUsername(getUsername(token)).toString());
        return loginUser;
    }


}
