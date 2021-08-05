package com.yeliheng.blogsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.exception.UnauthorizedException;
import com.yeliheng.blogsystem.exception.UnexpectedException;
import com.yeliheng.blogsystem.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Boolean login(String username, String password) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (Exception e){
            throw new UnauthorizedException("用户名或密码错误！");
        }
        return true;
    }
}
