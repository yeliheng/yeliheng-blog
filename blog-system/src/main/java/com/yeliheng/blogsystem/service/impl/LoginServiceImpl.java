package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.service.ILoginService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public String login(String username, String password,boolean rememberMe) {
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

        return tokenUtils.createToken(loginUser,rememberMe);
    }
}
