package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogcommon.constant.RedisKeys;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.RedisUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.service.ILoginService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, String> login(String username, String password, boolean rememberMe) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (Exception e){
            throw new GeneralException("用户名或密码错误！");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(!loginUser.getUser().isAdmin() && loginUser.getUser().getLocked()) {
            throw new GeneralException("用户已锁定，请联系管理员");
        }
        String loginToken = tokenUtils.createToken(loginUser,rememberMe);
        String refreshToken = tokenUtils.createRefreshToken(loginUser);
        Map<String ,String > tokenMap = new HashMap<>();
        tokenMap.put("token",loginToken);
        tokenMap.put("refreshToken",refreshToken);
        return tokenMap;
    }

    @Override
    public String refreshToken(String refreshToken) {
        LoginUser loginUser = tokenUtils.getLoginUserFromRefreshToken(refreshToken);
        if(loginUser == null) {
            throw new GeneralException("刷新token无效");
        }
        return tokenUtils.createToken(loginUser,false);
    }


}
